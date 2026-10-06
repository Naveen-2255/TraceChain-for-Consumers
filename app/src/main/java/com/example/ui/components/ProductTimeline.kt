package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LifecycleEvent
import com.example.ui.theme.DarkEmerald
import com.example.ui.theme.Emerald
import com.example.ui.theme.Graphite
import com.example.ui.theme.Ivory
import com.example.ui.theme.LightSurface
import com.example.ui.theme.SecondaryText
import com.example.ui.theme.TimelineConnector

@Composable
fun ProductTimeline(
    events: List<LifecycleEvent>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        events.forEachIndexed { index, event ->
            TimelineItem(
                event = event,
                isLast = index == events.lastIndex
            )
        }
    }
}

@Composable
private fun TimelineItem(
    event: LifecycleEvent,
    isLast: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Timeline node indicator + connecting line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(32.dp)
        ) {
            val nodeColor = when {
                event.isCurrent -> Emerald
                event.isCompleted -> DarkEmerald
                else -> TimelineConnector
            }

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(nodeColor),
                contentAlignment = Alignment.Center
            ) {
                if (event.isCompleted && !event.isCurrent) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Completed",
                        tint = Ivory,
                        modifier = Modifier.size(14.dp)
                    )
                } else if (event.isCurrent) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(DarkEmerald)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.FiberManualRecord,
                        contentDescription = null,
                        tint = Ivory,
                        modifier = Modifier.size(10.dp)
                    )
                }
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.5.dp)
                        .fillMaxHeight()
                        .background(TimelineConnector)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 12.dp else 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = event.type,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (event.isCurrent) DarkEmerald else Graphite
                )

                if (event.isCurrent) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Emerald.copy(alpha = 0.2f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Current Stage",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkEmerald
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = event.date,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = DarkEmerald.copy(alpha = 0.85f)
            )

            if (!event.organization.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = event.organization,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Graphite
                )
            }

            if (!event.location.isNullOrBlank()) {
                Text(
                    text = event.location,
                    fontSize = 12.sp,
                    color = SecondaryText
                )
            }

            if (!event.details.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(LightSurface)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = event.details,
                        fontSize = 12.sp,
                        color = SecondaryText,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}
