package com.example.data.repository

import com.example.data.model.AppNotification
import com.example.data.model.LifecycleEvent
import com.example.data.model.Product
import com.example.data.model.Purchase

object MockData {
    val initialProducts = listOf(
        Product(
            id = "prod_001",
            name = "Amul Taaza Milk 1L",
            batchNumber = "AM-2026-001",
            manufacturingDate = "21 Sep 2026",
            expiryDate = "28 Sep 2026",
            currentStatus = "Verified",
            qrCode = "TC-AMUL-001",
            authenticityStatus = "Verified",
            retailer = "ABC Supermarket",
            category = "Dairy",
            price = "₹54.00",
            originLocation = "Gujarat Dairy Cooperative, Anand",
            temperatureLog = "4°C (Cold Chain Maintained)",
            certifications = listOf("FSSAI A-1029", "TraceChain Block #782914", "ISO 22000"),
            daysUntilExpiry = 3
        ),
        Product(
            id = "prod_002",
            name = "Modern White Bread",
            batchNumber = "MD-2026-114",
            manufacturingDate = "20 Sep 2026",
            expiryDate = "25 Sep 2026",
            currentStatus = "Expiring Soon",
            qrCode = "TC-BREAD-114",
            authenticityStatus = "Verified",
            retailer = "Daily Fresh Mart",
            category = "Bakery",
            price = "₹45.00",
            originLocation = "Modern Bakeries, Bengaluru Hub",
            temperatureLog = "22°C (Ambient Dry Storage)",
            certifications = listOf("FSSAI B-8491", "TraceChain Block #783102"),
            daysUntilExpiry = 5
        ),
        Product(
            id = "prod_003",
            name = "Organic Wildflower Honey 500g",
            batchNumber = "OH-2026-042",
            manufacturingDate = "15 Sep 2026",
            expiryDate = "15 Sep 2027",
            currentStatus = "Verified",
            qrCode = "TC-HONEY-042",
            authenticityStatus = "Verified",
            retailer = "Nature's Basket",
            category = "Pantry",
            price = "₹349.00",
            originLocation = "Coorg Apiaries, Karnataka",
            temperatureLog = "24°C (Room Temperature)",
            certifications = listOf("India Organic Certified", "FSSAI Org-092", "TraceChain Block #779210"),
            daysUntilExpiry = 355
        ),
        Product(
            id = "prod_004",
            name = "Farm Fresh Eggs 12pk",
            batchNumber = "EG-2026-302",
            manufacturingDate = "22 Sep 2026",
            expiryDate = "29 Sep 2026",
            currentStatus = "Verified",
            qrCode = "TC-EGGS-302",
            authenticityStatus = "Verified",
            retailer = "ABC Supermarket",
            category = "Poultry",
            price = "₹96.00",
            originLocation = "Green Valley Poultry, Namakkal",
            temperatureLog = "12°C (Controlled Climate)",
            certifications = listOf("Cage Free Certified", "FSSAI A-3301", "TraceChain Block #784551"),
            daysUntilExpiry = 4
        ),
        Product(
            id = "prod_005",
            name = "Greek Yogurt Blueberry 200g",
            batchNumber = "GY-2026-089",
            manufacturingDate = "10 Sep 2026",
            expiryDate = "22 Sep 2026",
            currentStatus = "Expired",
            qrCode = "TC-YOGURT-089",
            authenticityStatus = "Verified",
            retailer = "Metro Cash & Carry",
            category = "Dairy",
            price = "₹75.00",
            originLocation = "Nilgiri Artisan Dairy, Ooty",
            temperatureLog = "3°C (Refrigerated)",
            certifications = listOf("Probiotic Verified", "TraceChain Block #781014"),
            daysUntilExpiry = -3
        )
    )

    val initialPurchases = listOf(
        Purchase(
            id = "purch_001",
            productId = "prod_001",
            productName = "Amul Taaza Milk 1L",
            batchNumber = "AM-2026-001",
            purchaseDate = "21 Sep 2026",
            expiryDate = "28 Sep 2026",
            retailer = "ABC Supermarket",
            verificationStatus = "Verified",
            price = "₹54.00",
            timestamp = 1758445200000L
        ),
        Purchase(
            id = "purch_002",
            productId = "prod_002",
            productName = "Modern White Bread",
            batchNumber = "MD-2026-114",
            purchaseDate = "20 Sep 2026",
            expiryDate = "25 Sep 2026",
            retailer = "Daily Fresh Mart",
            verificationStatus = "Expiring Soon",
            price = "₹45.00",
            timestamp = 1758358800000L
        ),
        Purchase(
            id = "purch_003",
            productId = "prod_003",
            productName = "Organic Wildflower Honey 500g",
            batchNumber = "OH-2026-042",
            purchaseDate = "15 Sep 2026",
            expiryDate = "15 Sep 2027",
            retailer = "Nature's Basket",
            verificationStatus = "Verified",
            price = "₹349.00",
            timestamp = 1757926800000L
        ),
        Purchase(
            id = "purch_004",
            productId = "prod_004",
            productName = "Farm Fresh Eggs 12pk",
            batchNumber = "EG-2026-302",
            purchaseDate = "23 Sep 2026",
            expiryDate = "29 Sep 2026",
            retailer = "ABC Supermarket",
            verificationStatus = "Verified",
            price = "₹96.00",
            timestamp = 1758618000000L
        ),
        Purchase(
            id = "purch_005",
            productId = "prod_005",
            productName = "Greek Yogurt Blueberry 200g",
            batchNumber = "GY-2026-089",
            purchaseDate = "12 Sep 2026",
            expiryDate = "22 Sep 2026",
            retailer = "Metro Cash & Carry",
            verificationStatus = "Expired",
            price = "₹75.00",
            timestamp = 1757667600000L
        )
    )

    val initialNotifications = listOf(
        AppNotification(
            id = "notif_001",
            title = "Product Expiring Soon",
            message = "Your Modern White Bread expires in 5 days.",
            type = "expiry",
            createdAt = "2 hours ago",
            isRead = false,
            productId = "prod_002"
        ),
        AppNotification(
            id = "notif_002",
            title = "Product Verified",
            message = "Amul Taaza Milk 1L was successfully verified on TraceChain.",
            type = "verification",
            createdAt = "Yesterday",
            isRead = false,
            productId = "prod_001"
        ),
        AppNotification(
            id = "notif_003",
            title = "Purchase Recorded",
            message = "Your purchase from ABC Supermarket has been added to your history.",
            type = "purchase",
            createdAt = "2 days ago",
            isRead = true,
            productId = "prod_004"
        ),
        AppNotification(
            id = "notif_004",
            title = "Expiry Warning",
            message = "Amul Taaza Milk 1L expires in 3 days on 28 Sep 2026.",
            type = "expiry",
            createdAt = "3 days ago",
            isRead = true,
            productId = "prod_001"
        )
    )

    fun getLifecycleEventsForProduct(productId: String): List<LifecycleEvent> {
        return when (productId) {
            "prod_001" -> listOf(
                LifecycleEvent(
                    id = "lc_1",
                    productId = productId,
                    type = "Manufactured",
                    date = "21 Sep 2026, 05:30 AM",
                    location = "Anand Processing Plant, Gujarat",
                    organization = "Amul Dairy Cooperative",
                    details = "Batch #AM-2026-001 pasteurized and quality checked.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_2",
                    productId = productId,
                    type = "Packed",
                    date = "21 Sep 2026, 08:15 AM",
                    location = "Automated Packaging Line 3",
                    organization = "Amul Packaging Division",
                    details = "Sealed in aseptic recyclable tetra pack.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_3",
                    productId = productId,
                    type = "Stored",
                    date = "21 Sep 2026, 11:00 AM",
                    location = "Western Cold Chain Hub",
                    organization = "CoolChain Logistics India",
                    details = "Temperature: 4°C continuous monitoring.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_4",
                    productId = productId,
                    type = "Distributed",
                    date = "21 Sep 2026, 02:45 PM",
                    location = "Refrigerated Transit Route KA-04",
                    organization = "SwiftRoute Logistics",
                    details = "Delivered to Bengaluru Regional Center.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_5",
                    productId = productId,
                    type = "Retail",
                    date = "21 Sep 2026, 04:30 PM",
                    location = "ABC Supermarket, Shelf C4",
                    organization = "ABC Supermarket Ltd.",
                    details = "Scanned into store inventory system.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_6",
                    productId = productId,
                    type = "Purchased",
                    date = "21 Sep 2026, 07:15 PM",
                    location = "Register #2",
                    organization = "Consumer Naveen Joseph",
                    details = "Verified on TraceChain at checkout.",
                    isCompleted = true,
                    isCurrent = true
                )
            )
            "prod_002" -> listOf(
                LifecycleEvent(
                    id = "lc_201",
                    productId = productId,
                    type = "Manufactured",
                    date = "20 Sep 2026, 04:00 AM",
                    location = "Modern Bakehouse, Peenya",
                    organization = "Modern Food Enterprises",
                    details = "Ingredients inspected, oven batch #MD-114.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_202",
                    productId = productId,
                    type = "Packed",
                    date = "20 Sep 2026, 06:30 AM",
                    location = "Baking Line B2",
                    organization = "Modern Packaging",
                    details = "Nitrogen flushed, tamper evident seal.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_203",
                    productId = productId,
                    type = "Distributed",
                    date = "20 Sep 2026, 08:30 AM",
                    location = "City Direct Van 12",
                    organization = "FreshExpress Logistics",
                    details = "Morning fresh dispatch.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_204",
                    productId = productId,
                    type = "Retail",
                    date = "20 Sep 2026, 09:45 AM",
                    location = "Daily Fresh Mart, Indiranagar",
                    organization = "Daily Fresh Retail",
                    details = "Stocked on Bakery Display.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_205",
                    productId = productId,
                    type = "Purchased",
                    date = "20 Sep 2026, 11:20 AM",
                    location = "Counter 1",
                    organization = "Consumer Naveen Joseph",
                    details = "TraceChain QR verified.",
                    isCompleted = true,
                    isCurrent = true
                )
            )
            else -> listOf(
                LifecycleEvent(
                    id = "lc_def_1",
                    productId = productId,
                    type = "Manufactured",
                    date = "15 Sep 2026",
                    location = "Certified Production Facility",
                    organization = "Authorized Manufacturer",
                    details = "Quality verification verified on blockchain node.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_def_2",
                    productId = productId,
                    type = "Packed",
                    date = "16 Sep 2026",
                    location = "Packaging Center",
                    organization = "Quality Pack Ltd.",
                    details = "TraceChain cryptographic tag applied.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_def_3",
                    productId = productId,
                    type = "Distributed",
                    date = "18 Sep 2026",
                    location = "State Logistics Hub",
                    organization = "Reliable Freight Co.",
                    details = "Dispatched under standard handling protocols.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_def_4",
                    productId = productId,
                    type = "Retail",
                    date = "20 Sep 2026",
                    location = "Authorized Partner Store",
                    organization = "Retail Partner",
                    details = "Stock received and scanned into distributed ledger.",
                    isCompleted = true,
                    isCurrent = false
                ),
                LifecycleEvent(
                    id = "lc_def_5",
                    productId = productId,
                    type = "Purchased",
                    date = "21 Sep 2026",
                    location = "Checkout Point",
                    organization = "Consumer",
                    details = "Final custody transfer recorded.",
                    isCompleted = true,
                    isCurrent = true
                )
            )
        }
    }
}
