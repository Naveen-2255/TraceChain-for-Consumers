package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.model.AppNotification
import com.example.data.model.Product
import com.example.data.model.Purchase

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: String,
    val name: String,
    val batchNumber: String,
    val manufacturingDate: String,
    val expiryDate: String,
    val currentStatus: String,
    val qrCode: String,
    val authenticityStatus: String,
    val retailer: String,
    val category: String,
    val price: String,
    val originLocation: String,
    val temperatureLog: String,
    val certificationsCsv: String,
    val daysUntilExpiry: Int
) {
    fun toDomainModel(): Product = Product(
        id = id,
        name = name,
        batchNumber = batchNumber,
        manufacturingDate = manufacturingDate,
        expiryDate = expiryDate,
        currentStatus = currentStatus,
        qrCode = qrCode,
        authenticityStatus = authenticityStatus,
        retailer = retailer,
        category = category,
        price = price,
        originLocation = originLocation,
        temperatureLog = temperatureLog,
        certifications = certificationsCsv.split(",").filter { it.isNotBlank() },
        daysUntilExpiry = daysUntilExpiry
    )

    companion object {
        fun fromDomain(p: Product): ProductEntity = ProductEntity(
            id = p.id,
            name = p.name,
            batchNumber = p.batchNumber,
            manufacturingDate = p.manufacturingDate,
            expiryDate = p.expiryDate,
            currentStatus = p.currentStatus,
            qrCode = p.qrCode,
            authenticityStatus = p.authenticityStatus,
            retailer = p.retailer,
            category = p.category,
            price = p.price,
            originLocation = p.originLocation,
            temperatureLog = p.temperatureLog,
            certificationsCsv = p.certifications.joinToString(","),
            daysUntilExpiry = p.daysUntilExpiry
        )
    }
}

@Entity(tableName = "purchases")
data class PurchaseEntity(
    @PrimaryKey val id: String,
    val productId: String,
    val productName: String,
    val batchNumber: String,
    val purchaseDate: String,
    val expiryDate: String,
    val retailer: String,
    val verificationStatus: String,
    val price: String,
    val timestamp: Long
) {
    fun toDomainModel(): Purchase = Purchase(
        id = id,
        productId = productId,
        productName = productName,
        batchNumber = batchNumber,
        purchaseDate = purchaseDate,
        expiryDate = expiryDate,
        retailer = retailer,
        verificationStatus = verificationStatus,
        price = price,
        timestamp = timestamp
    )

    companion object {
        fun fromDomain(p: Purchase): PurchaseEntity = PurchaseEntity(
            id = p.id,
            productId = p.productId,
            productName = p.productName,
            batchNumber = p.batchNumber,
            purchaseDate = p.purchaseDate,
            expiryDate = p.expiryDate,
            retailer = p.retailer,
            verificationStatus = p.verificationStatus,
            price = p.price,
            timestamp = p.timestamp
        )
    }
}

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val title: String,
    val message: String,
    val type: String,
    val createdAt: String,
    val isRead: Boolean,
    val productId: String?
) {
    fun toDomainModel(): AppNotification = AppNotification(
        id = id,
        title = title,
        message = message,
        type = type,
        createdAt = createdAt,
        isRead = isRead,
        productId = productId
    )

    companion object {
        fun fromDomain(n: AppNotification): NotificationEntity = NotificationEntity(
            id = n.id,
            title = n.title,
            message = n.message,
            type = n.type,
            createdAt = n.createdAt,
            isRead = n.isRead,
            productId = n.productId
        )
    }
}
