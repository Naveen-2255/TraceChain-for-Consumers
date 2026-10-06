package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.NotificationEntity
import com.example.data.local.ProductEntity
import com.example.data.local.PurchaseEntity
import com.example.data.model.AppNotification
import com.example.data.model.LifecycleEvent
import com.example.data.model.Product
import com.example.data.model.Purchase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class TraceChainRepository(private val database: AppDatabase) {

    private val productDao = database.productDao()
    private val purchaseDao = database.purchaseDao()
    private val notificationDao = database.notificationDao()

    val allPurchases: Flow<List<Purchase>> = purchaseDao.getAllPurchases().map { list ->
        list.map { it.toDomainModel() }
    }

    val allProducts: Flow<List<Product>> = productDao.getAllProducts().map { list ->
        list.map { it.toDomainModel() }
    }

    val expiringPurchases: Flow<List<Purchase>> = purchaseDao.getExpiringPurchases().map { list ->
        list.map { it.toDomainModel() }
    }

    val allNotifications: Flow<List<AppNotification>> = notificationDao.getAllNotifications().map { list ->
        list.map { it.toDomainModel() }
    }

    val unreadNotificationCount: Flow<Int> = notificationDao.getUnreadCount()

    suspend fun seedDatabaseIfEmpty() = withContext(Dispatchers.IO) {
        val existingPurchases = purchaseDao.getAllPurchases().first()
        if (existingPurchases.isEmpty()) {
            productDao.insertProducts(MockData.initialProducts.map { ProductEntity.fromDomain(it) })
            purchaseDao.insertPurchases(MockData.initialPurchases.map { PurchaseEntity.fromDomain(it) })
            notificationDao.insertNotifications(MockData.initialNotifications.map { NotificationEntity.fromDomain(it) })
        }
    }

    suspend fun getProductById(id: String): Product? = withContext(Dispatchers.IO) {
        val entity = productDao.getProductById(id)
        entity?.toDomainModel() ?: MockData.initialProducts.find { it.id == id }
    }

    suspend fun getProductByQr(qrCode: String): Product? = withContext(Dispatchers.IO) {
        val entity = productDao.getProductByQr(qrCode)
        entity?.toDomainModel() ?: MockData.initialProducts.find { it.qrCode.equals(qrCode, ignoreCase = true) }
    }

    fun getLifecycleEvents(productId: String): List<LifecycleEvent> {
        return MockData.getLifecycleEventsForProduct(productId)
    }

    suspend fun markNotificationAsRead(id: String) = withContext(Dispatchers.IO) {
        notificationDao.markAsRead(id)
    }

    suspend fun markAllNotificationsAsRead() = withContext(Dispatchers.IO) {
        notificationDao.markAllAsRead()
    }

    suspend fun recordPurchaseFromProduct(product: Product) = withContext(Dispatchers.IO) {
        val purchase = Purchase(
            id = "purch_${System.currentTimeMillis()}",
            productId = product.id,
            productName = product.name,
            batchNumber = product.batchNumber,
            purchaseDate = "Today",
            expiryDate = product.expiryDate,
            retailer = product.retailer,
            verificationStatus = product.currentStatus,
            price = product.price,
            timestamp = System.currentTimeMillis()
        )
        purchaseDao.insertPurchase(PurchaseEntity.fromDomain(purchase))

        val notif = AppNotification(
            id = "notif_${System.currentTimeMillis()}",
            title = "Product Verified & Recorded",
            message = "${product.name} (${product.batchNumber}) verified and logged to your history.",
            type = "verification",
            createdAt = "Just now",
            isRead = false,
            productId = product.id
        )
        notificationDao.insertNotification(NotificationEntity.fromDomain(notif))
    }
}
