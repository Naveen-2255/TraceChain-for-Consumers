package com.example.data.model

data class Product(
    val id: String,
    val name: String,
    val batchNumber: String,
    val manufacturingDate: String,
    val expiryDate: String,
    val currentStatus: String, // "Verified", "Expiring Soon", "Expired", "Recalled"
    val qrCode: String,
    val authenticityStatus: String, // "Verified", "Unverified", "Invalid"
    val retailer: String = "ABC Supermarket",
    val category: String = "Groceries",
    val price: String = "₹65.00",
    val originLocation: String = "Karnataka, India",
    val temperatureLog: String = "4°C (Optimal Cold Chain)",
    val certifications: List<String> = listOf("FSSAI Verified", "TraceChain Hash #8F2A9C"),
    val daysUntilExpiry: Int = 5,
    val image: String? = null
)

data class Purchase(
    val id: String,
    val productId: String,
    val productName: String,
    val batchNumber: String,
    val purchaseDate: String,
    val expiryDate: String,
    val retailer: String,
    val verificationStatus: String, // "Verified", "Expiring Soon", "Expired"
    val price: String = "₹65.00",
    val timestamp: Long = System.currentTimeMillis()
)

data class LifecycleEvent(
    val id: String,
    val productId: String,
    val type: String, // "Manufactured", "Packed", "Stored", "Distributed", "Retail", "Purchased"
    val date: String,
    val location: String? = null,
    val organization: String? = null,
    val details: String? = null,
    val isCompleted: Boolean = true,
    val isCurrent: Boolean = false
)

data class AppNotification(
    val id: String,
    val title: String,
    val message: String,
    val type: String, // "expiry", "verification", "purchase"
    val createdAt: String,
    val isRead: Boolean = false,
    val productId: String? = null
)

data class UserProfile(
    val name: String = "Naveen Joseph",
    val initials: String = "NJ",
    val email: String = "naveenjosephvadakkel@gmail.com",
    val phone: String = "+91 98765 43210",
    val location: String = "Bengaluru, Karnataka",
    val expiryAlertsEnabled: Boolean = true,
    val productNotificationsEnabled: Boolean = true
)
