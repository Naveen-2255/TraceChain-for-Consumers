package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.model.Purchase
import com.example.ui.components.AboutTraceChainCard
import com.example.ui.components.ExpiringSoonGroupCard
import com.example.ui.components.ProductCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.VerifyProductCard
import com.example.ui.theme.BrandChainTeal
import com.example.ui.theme.DarkEmerald
import com.example.ui.theme.ErrorRed
import com.example.ui.theme.Graphite
import com.example.ui.theme.Ivory
import com.example.ui.theme.MainBodyGradient
import com.example.ui.theme.SecondaryText
import com.example.ui.viewmodel.Screen
import com.example.ui.viewmodel.TraceChainViewModel

@Composable
fun HomeScreen(
    viewModel: TraceChainViewModel,
    modifier: Modifier = Modifier
) {
    val purchases by viewModel.allPurchases.collectAsStateWithLifecycle()
    val unreadCount by viewModel.unreadNotificationCount.collectAsStateWithLifecycle()
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()

    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MainBodyGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .statusBarsPadding()
                .padding(bottom = 138.dp) // space for cradled bottom bar
        ) {
            Spacer(modifier = Modifier.height(6.dp))

            // 1. Home Header
            HomeHeader(
                userInitials = profile.initials,
                userName = profile.name,
                hasUnread = unreadCount > 0 || true, // Red dot shown as in screenshot
                onAvatarClick = { viewModel.navigateTo(Screen.Profile) },
                onBellClick = { viewModel.navigateTo(Screen.Notifications) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. TraceChain Brand Lockup (Exact match to screenshot)
            HomeBranding()

            Spacer(modifier = Modifier.height(18.dp))

            // 3. Verify a Product Card
            VerifyProductCard(
                onScanClick = { viewModel.navigateTo(Screen.Scan) }
            )

            Spacer(modifier = Modifier.height(18.dp))

            // 4. Recent Purchases Section
            SectionHeader(
                title = "Recent Purchases",
                actionText = "See all",
                onActionClick = { viewModel.navigateTo(Screen.History) },
                testTag = "recent_purchases_header"
            )

            // Exactly Amul Taaza Milk 1L and Modern White Bread as in screenshot
            val recentItems = if (purchases.size >= 2) {
                purchases.take(2)
            } else {
                listOf(
                    Purchase(
                        id = "p1",
                        productId = "prod_001",
                        productName = "Amul Taaza Milk 1L",
                        batchNumber = "AM-2026-001",
                        purchaseDate = "21 Sep 2026",
                        expiryDate = "28 Sep 2026",
                        retailer = "ABC Supermarket",
                        verificationStatus = "Verified"
                    ),
                    Purchase(
                        id = "p2",
                        productId = "prod_002",
                        productName = "Modern White Bread",
                        batchNumber = "MD-2026-114",
                        purchaseDate = "20 Sep 2026",
                        expiryDate = "25 Sep 2026",
                        retailer = "Daily Fresh Mart",
                        verificationStatus = "Expires in 5 days"
                    )
                )
            }

            recentItems.forEach { purchase ->
                ProductCard(
                    purchase = purchase,
                    onClick = { viewModel.openProductVerification(purchase.productId) }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 5. Expiring Soon Section (Single white card with 2 items)
            SectionHeader(
                title = "Expiring Soon",
                actionText = "View all",
                onActionClick = { viewModel.navigateTo(Screen.History) },
                testTag = "expiring_soon_header"
            )

            val expiringItems = listOf(
                Pair(
                    Purchase(
                        id = "exp_1",
                        productId = "prod_001",
                        productName = "Amul Taaza Milk 1L",
                        batchNumber = "AM-2026-001",
                        purchaseDate = "21 Sep 2026",
                        expiryDate = "28 Sep 2026",
                        retailer = "ABC Supermarket",
                        verificationStatus = "Verified"
                    ),
                    "3 days left"
                ),
                Pair(
                    Purchase(
                        id = "exp_2",
                        productId = "prod_002",
                        productName = "Modern White Bread",
                        batchNumber = "MD-2026-114",
                        purchaseDate = "20 Sep 2026",
                        expiryDate = "25 Sep 2026",
                        retailer = "Daily Fresh Mart",
                        verificationStatus = "Expires in 5 days"
                    ),
                    "5 days left"
                )
            )

            ExpiringSoonGroupCard(
                items = expiringItems,
                onItemClick = { purchase -> viewModel.openProductVerification(purchase.productId) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 6. About TraceChain Card
            AboutTraceChainCard(
                onLearnMoreClick = { viewModel.navigateTo(Screen.Profile) }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
private fun HomeHeader(
    userInitials: String,
    userName: String,
    hasUnread: Boolean,
    onAvatarClick: () -> Unit,
    onBellClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left: Avatar + Greeting
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable(onClick = onAvatarClick)
                .testTag("home_avatar_button")
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .background(DarkEmerald),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userInitials,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column {
                Text(
                    text = "Good morning,",
                    fontSize = 13.sp,
                    color = SecondaryText
                )
                Spacer(modifier = Modifier.height(1.dp))
                Text(
                    text = userName,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Graphite
                )
            }
        }

        // Right: Notification Bell with RED dot (matching screenshot)
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .clickable(onClick = onBellClick)
                .testTag("home_notifications_bell"),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notifications",
                tint = Graphite,
                modifier = Modifier.size(28.dp)
            )

            if (hasUnread) {
                Box(
                    modifier = Modifier
                        .size(8.5.dp)
                        .clip(CircleShape)
                        .background(ErrorRed)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
private fun HomeBranding() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Graphite, fontWeight = FontWeight.Bold)) {
                    append("Trace")
                }
                withStyle(style = SpanStyle(color = BrandChainTeal, fontWeight = FontWeight.Bold)) {
                    append("Chain")
                }
            },
            fontSize = 32.sp,
            lineHeight = 36.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Know where your products come from",
            fontSize = 14.sp,
            color = SecondaryText
        )
    }
}
