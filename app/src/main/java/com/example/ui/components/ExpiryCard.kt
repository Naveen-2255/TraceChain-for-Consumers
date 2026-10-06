package com.example.ui.components

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Purchase
import com.example.ui.theme.AmberBadgeBg
import com.example.ui.theme.AmberBadgeText
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.ChevronMuted
import com.example.ui.theme.Graphite
import com.example.ui.theme.LightSurface
import com.example.ui.theme.SecondaryText

@Composable
fun ExpiringSoonGroupCard(
    items: List<Pair<Purchase, String>>, // Purchase and "X days left"
    onItemClick: (Purchase) -> Unit,
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
            .clip(RoundedCornerShape(22.dp)),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = LightSurface),
        border = BorderStroke(1.dp, CardBorderColor)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEachIndexed { index, (purchase, daysLeftLabel) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(purchase) }
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                        .testTag("expiring_item_${purchase.productId}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Packaging thumbnail
                    ProductThumbnail(
                        productName = purchase.productName,
                        width = 48,
                        height = 56
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    // Text Details
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = purchase.productName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Graphite
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Expires on ${purchase.expiryDate}",
                            fontSize = 13.sp,
                            color = SecondaryText
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Badge: e.g. "3 days left"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(AmberBadgeBg)
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                    ) {
                        Text(
                            text = daysLeftLabel,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = AmberBadgeText
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "View",
                        tint = ChevronMuted,
                        modifier = Modifier.size(20.dp)
                    )
                }

                if (index < items.lastIndex) {
                    HorizontalDivider(
                        color = CardBorderColor.copy(alpha = 0.6f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}
