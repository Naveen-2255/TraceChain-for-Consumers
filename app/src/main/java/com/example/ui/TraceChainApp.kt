package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AppBottomNavigation
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProductJourneyScreen
import com.example.ui.screens.ProductVerificationScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ScanScreen
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.TraceChainViewModel

@Composable
fun TraceChainApp(
    viewModel: TraceChainViewModel = viewModel()
) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()

    // Handle system back navigation
    BackHandler(enabled = currentScreen !is Screen.Home) {
        if (!viewModel.navigateBack()) {
            viewModel.navigateTo(Screen.Home)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = androidx.compose.foundation.layout.WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Main Screen Content with smooth fade transitions
            AnimatedContent(
                targetState = currentScreen,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { screen ->
                when (screen) {
                    is Screen.Home -> HomeScreen(viewModel = viewModel)
                    is Screen.Scan -> ScanScreen(viewModel = viewModel)
                    is Screen.History -> HistoryScreen(viewModel = viewModel)
                    is Screen.ProductVerification -> ProductVerificationScreen(
                        productId = screen.productId,
                        isInvalid = screen.isInvalid,
                        invalidCode = screen.invalidCode,
                        viewModel = viewModel
                    )
                    is Screen.ProductJourney -> ProductJourneyScreen(
                        productId = screen.productId,
                        viewModel = viewModel
                    )
                    is Screen.Notifications -> NotificationsScreen(viewModel = viewModel)
                    is Screen.Profile -> ProfileScreen(viewModel = viewModel)
                }
            }

            // Bottom Navigation visible on primary navigation tabs (Scan, Home, History)
            val showBottomNav = currentScreen is Screen.Home ||
                    currentScreen is Screen.Scan ||
                    currentScreen is Screen.History

            if (showBottomNav) {
                AppBottomNavigation(
                    currentTab = currentTab,
                    onTabSelected = { tab -> viewModel.selectNavTab(tab) },
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
            }
        }
    }
}
