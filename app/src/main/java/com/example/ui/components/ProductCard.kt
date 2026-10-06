package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Purchase
import com.example.ui.theme.AmberBadgeBg
import com.example.ui.theme.AmberBadgeText
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.ChevronMuted
import com.example.ui.theme.Graphite
import com.example.ui.theme.LightSurface
import com.example.ui.theme.FloralWhite
import com.example.ui.theme.MintBadgeBg
import com.example.ui.theme.MintBadgeText
import com.example.ui.theme.SecondaryText

@Composable
fun ProductCard(
    purchase: Purchase,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(22.dp),
                ambientColor = Color(0x08000000),
                spotColor = Color(0x08000000)
            )
            .clip(RoundedCornerShape(22.dp))
            .clickable(onClick = onClick)
            .testTag("product_card_${purchase.productId}"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = LightSurface),
        border = BorderStroke(1.dp, CardBorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Packaging Thumbnail
            ProductThumbnail(productName = purchase.productName)

            Spacer(modifier = Modifier.width(14.dp))

            // Details: Name, Batch, Purchased, Expires
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = purchase.productName,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Graphite,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "Batch: ${purchase.batchNumber}",
                    fontSize = 12.sp,
                    color = SecondaryText
                )

                Spacer(modifier = Modifier.height(1.dp))

                Text(
                    text = "Purchased: ${purchase.purchaseDate}",
                    fontSize = 12.sp,
                    color = SecondaryText
                )

                Spacer(modifier = Modifier.height(1.dp))

                Text(
                    text = "Expires: ${purchase.expiryDate}",
                    fontSize = 12.sp,
                    color = SecondaryText
                )
            }

            // Right side: Badge on top, Chevron on right
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(72.dp)
            ) {
                // Top-right status badge
                ProductStatusPill(status = purchase.verificationStatus)

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Details",
                    tint = ChevronMuted,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Composable
fun ProductThumbnail(
    productName: String,
    modifier: Modifier = Modifier,
    width: Int = 62,
    height: Int = 74
) {
    val isMilk = productName.contains("Milk", ignoreCase = true)
    val isBread = productName.contains("Bread", ignoreCase = true)

    Box(
        modifier = modifier
            .size(width = width.dp, height = height.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(FloralWhite),
        contentAlignment = Alignment.Center
    ) {
        if (isMilk) {
            Image(
                painter = painterResource(id = R.drawable.ic_amul_milk),
                contentDescription = "Amul Milk",
                modifier = Modifier.size(width = (width - 12).dp, height = (height - 10).dp)
            )
        } else if (isBread) {
            Image(
                painter = painterResource(id = R.drawable.ic_modern_bread),
                contentDescription = "Modern Bread",
                modifier = Modifier.size(width = (width - 12).dp, height = (height - 10).dp)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_amul_milk),
                contentDescription = productName,
                modifier = Modifier.size(width = (width - 12).dp, height = (height - 10).dp)
            )
        }
    }
}

@Composable
private fun ProductStatusPill(status: String) {
    val isVerified = status.equals("Verified", ignoreCase = true)

    if (isVerified) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MintBadgeBg)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(MintBadgeText),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                }
                Text(
                    text = "Verified",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MintBadgeText
                )
            }
        }
    } else {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(AmberBadgeBg)
                .padding(horizontal = 10.dp, vertical = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = AmberBadgeText,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = status,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = AmberBadgeText
                )
            }
        }
    }
}
