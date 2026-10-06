package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.AppNotification
import com.example.data.model.LifecycleEvent
import com.example.data.model.Product
import com.example.data.model.Purchase
import com.example.data.model.UserProfile
import com.example.data.repository.MockData
import com.example.data.repository.TraceChainRepository
import com.example.ui.components.NavTab
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed class Screen {
    data object Home : Screen()
    data object Scan : Screen()
    data object History : Screen()
    data class ProductVerification(val productId: String, val isInvalid: Boolean = false, val invalidCode: String? = null) : Screen()
    data class ProductJourney(val productId: String) : Screen()
    data object Notifications : Screen()
    data object Profile : Screen()
}

class TraceChainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TraceChainRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = TraceChainRepository(database)
        viewModelScope.launch {
            repository.seedDatabaseIfEmpty()
        }
    }

    // Navigation Stack
    private val _screenStack = MutableStateFlow<List<Screen>>(listOf(Screen.Home))
    val currentScreen: StateFlow<Screen> = _screenStack.combine(MutableStateFlow(Unit)) { stack, _ ->
        stack.lastOrNull() ?: Screen.Home
    }.stateIn(viewModelScope, SharingStarted.Eagerly, Screen.Home)

    val currentTab: StateFlow<NavTab> = currentScreen.combine(MutableStateFlow(Unit)) { screen, _ ->
        when (screen) {
            is Screen.Scan -> NavTab.SCAN
            is Screen.History -> NavTab.HISTORY
            else -> NavTab.HOME
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, NavTab.HOME)

    // Data streams
    val allPurchases: StateFlow<List<Purchase>> = repository.allPurchases
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val expiringPurchases: StateFlow<List<Purchase>> = repository.expiringPurchases
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allNotifications: StateFlow<List<AppNotification>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationCount: StateFlow<Int> = repository.unreadNotificationCount
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Selected product for verification
    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct.asStateFlow()

    // Selected product lifecycle events
    private val _selectedEvents = MutableStateFlow<List<LifecycleEvent>>(emptyList())
    val selectedEvents: StateFlow<List<LifecycleEvent>> = _selectedEvents.asStateFlow()

    // User Profile
    private val _userProfile = MutableStateFlow(UserProfile())
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // History filter & search
    private val _historySearchQuery = MutableStateFlow("")
    val historySearchQuery: StateFlow<String> = _historySearchQuery.asStateFlow()

    private val _historyFilterTab = MutableStateFlow("All")
    val historyFilterTab: StateFlow<String> = _historyFilterTab.asStateFlow()

    // Navigation methods
    fun navigateTo(screen: Screen) {
        val current = _screenStack.value
        if (screen is Screen.Home) {
            _screenStack.value = listOf(Screen.Home)
        } else if (screen is Screen.Scan) {
            // Replace or push
            _screenStack.value = current.filterNot { it is Screen.Scan } + screen
        } else if (screen is Screen.History) {
            _screenStack.value = current.filterNot { it is Screen.History } + screen
        } else {
            _screenStack.value = current + screen
        }
    }

    fun navigateBack(): Boolean {
        val current = _screenStack.value
        return if (current.size > 1) {
            _screenStack.value = current.dropLast(1)
            true
        } else {
            false
        }
    }

    fun selectNavTab(tab: NavTab) {
        when (tab) {
            NavTab.SCAN -> navigateTo(Screen.Scan)
            NavTab.HOME -> navigateTo(Screen.Home)
            NavTab.HISTORY -> navigateTo(Screen.History)
        }
    }

    fun openProductVerification(productId: String) {
        viewModelScope.launch {
            val product = repository.getProductById(productId)
            _selectedProduct.value = product
            if (product != null) {
                _selectedEvents.value = repository.getLifecycleEvents(product.id)
                navigateTo(Screen.ProductVerification(productId = productId))
            } else {
                navigateTo(Screen.ProductVerification(productId = productId, isInvalid = true, invalidCode = productId))
            }
        }
    }

    fun openProductJourney(productId: String) {
        _selectedEvents.value = repository.getLifecycleEvents(productId)
        navigateTo(Screen.ProductJourney(productId = productId))
    }

    fun processScannedQr(code: String) {
        viewModelScope.launch {
            val product = repository.getProductByQr(code)
            if (product != null) {
                _selectedProduct.value = product
                _selectedEvents.value = repository.getLifecycleEvents(product.id)
                // Automatically log to recent purchases if not present
                repository.recordPurchaseFromProduct(product)
                navigateTo(Screen.ProductVerification(productId = product.id))
            } else {
                _selectedProduct.value = null
                navigateTo(Screen.ProductVerification(productId = "unknown", isInvalid = true, invalidCode = code))
            }
        }
    }

    fun markNotificationRead(id: String) {
        viewModelScope.launch {
            repository.markNotificationAsRead(id)
        }
    }

    fun markAllNotificationsRead() {
        viewModelScope.launch {
            repository.markAllNotificationsAsRead()
        }
    }

    fun setHistorySearchQuery(query: String) {
        _historySearchQuery.value = query
    }

    fun setHistoryFilterTab(tab: String) {
        _historyFilterTab.value = tab
    }

    fun toggleExpiryAlerts() {
        _userProfile.value = _userProfile.value.copy(
            expiryAlertsEnabled = !_userProfile.value.expiryAlertsEnabled
        )
    }

    fun toggleProductNotifications() {
        _userProfile.value = _userProfile.value.copy(
            productNotificationsEnabled = !_userProfile.value.productNotificationsEnabled
        )
    }
}
