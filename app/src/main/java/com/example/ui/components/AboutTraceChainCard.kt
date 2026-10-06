package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Link
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CardBorderColor
import com.example.ui.theme.ChevronMuted
import com.example.ui.theme.Graphite
import com.example.ui.theme.LightSurface
import com.example.ui.theme.MintBadgeBg
import com.example.ui.theme.MintBadgeText
import com.example.ui.theme.SecondaryText

@Composable
fun AboutTraceChainCard(
    onLearnMoreClick: () -> Unit,
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
            .clickable(onClick = onLearnMoreClick)
            .testTag("about_tracechain_card"),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = LightSurface),
        border = BorderStroke(1.dp, CardBorderColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Circular link badge
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(MintBadgeBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Link,
                    contentDescription = null,
                    tint = MintBadgeText,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "About TraceChain",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Graphite
                )

                Spacer(modifier = Modifier.size(3.dp))

                Text(
                    text = "A blockchain-based product traceability system that lets you verify authenticity and track the complete product journey.",
                    fontSize = 13.sp,
                    color = SecondaryText,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Journey illustration (Box, route, truck, pin)
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = com.example.R.drawable.ic_trace_journey_art),
                contentDescription = null,
                modifier = Modifier.size(width = 68.dp, height = 52.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Learn more",
                tint = ChevronMuted,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
