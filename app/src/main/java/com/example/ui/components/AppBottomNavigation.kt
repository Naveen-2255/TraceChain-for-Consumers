package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.DarkCoffee
import com.example.ui.theme.DarkEmerald
import com.example.ui.theme.FloralWhite
import com.example.ui.theme.MintActivePill
import kotlin.math.asin
import kotlin.math.sqrt

enum class NavTab {
    SCAN, HOME, HISTORY
}

@Composable
fun AppBottomNavigation(
    currentTab: NavTab,
    onTabSelected: (NavTab) -> Unit,
    modifier: Modifier = Modifier
) {
    // Outer Box: Permanent concentric circular arc cradle anchored in the center (no transfer)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val topY = 32.dp.toPx()
                val cornerR = 20.dp.toPx()

                // Center cradle geometry (permanently centered)
                val btnDiameter = 60.dp.toPx()
                val btnRadius = btnDiameter / 2f
                val cradleMargin = 6.dp.toPx()          // Exact 6dp uniform concentric gap
                val rCradle = btnRadius + cradleMargin  // 36dp concave cradle radius
                val rShoulder = 14.dp.toPx()            // 14dp convex shoulder flare
                val cy = topY + 4.dp.toPx()             // Concentric center shared with Home button (36dp from top)

                val Ys = topY + rShoulder
                val deltaY = Ys - cy
                val sumR = rCradle + rShoulder
                val deltaX = sqrt((sumR * sumR) - (deltaY * deltaY))

                val sinBeta = (deltaY / sumR).coerceIn(-1f, 1f)
                val betaDeg = Math.toDegrees(asin(sinBeta.toDouble())).toFloat()

                // Fixed center horizontal position
                val cx = size.width * 0.5f

                val path = Path().apply {
                    reset()
                    // Top-left rounded corner
                    moveTo(0f, topY + cornerR)
                    quadraticTo(0f, topY, cornerR, topY)

                    // Flat horizontal line to left shoulder
                    lineTo(cx - deltaX, topY)

                    // 1. Left convex circular shoulder fillet
                    arcTo(
                        rect = Rect(
                            left = cx - deltaX - rShoulder,
                            top = Ys - rShoulder,
                            right = cx - deltaX + rShoulder,
                            bottom = Ys + rShoulder
                        ),
                        startAngleDegrees = 270f,
                        sweepAngleDegrees = 90f - betaDeg,
                        forceMoveTo = false
                    )

                    // 2. Concentric circular concave cradle scoop
                    arcTo(
                        rect = Rect(
                            left = cx - rCradle,
                            top = cy - rCradle,
                            right = cx + rCradle,
                            bottom = cy + rCradle
                        ),
                        startAngleDegrees = 180f - betaDeg,
                        sweepAngleDegrees = -(180f - 2f * betaDeg),
                        forceMoveTo = false
                    )

                    // 3. Right convex circular shoulder fillet
                    arcTo(
                        rect = Rect(
                            left = cx + deltaX - rShoulder,
                            top = Ys - rShoulder,
                            right = cx + deltaX + rShoulder,
                            bottom = Ys + rShoulder
                        ),
                        startAngleDegrees = 180f + betaDeg,
                        sweepAngleDegrees = 90f - betaDeg,
                        forceMoveTo = false
                    )

                    // Flat horizontal line to top-right corner
                    lineTo(size.width - cornerR, topY)
                    quadraticTo(size.width, topY, size.width, topY + cornerR)

                    // Down to bottom-right, across bottom of screen, and close
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(path = path, color = DarkEmerald)
            }
    ) {
        // Continuous animation transitions
        val infiniteTransition = rememberInfiniteTransition(label = "nav_animations")

        // 1. Scan animation: Up-and-down laser scanning beam & bounce
        val scanLaserOffset by infiniteTransition.animateFloat(
            initialValue = -12f,
            targetValue = 12f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 700, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scan_laser_anim"
        )

        val scanBounce by infiniteTransition.animateFloat(
            initialValue = -5f,
            targetValue = 5f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 700, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "scan_bounce_anim"
        )

        // 2. History animation: Continuous 360-degree rotation
        val historyRotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1800, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = "history_rotation_anim"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(98.dp),
            verticalAlignment = Alignment.Top
        ) {
            // TAB 1: SCAN (Up-and-down laser scanning animation, NO circle transfer)
            val isScanSelected = currentTab == NavTab.SCAN
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Tab,
                        onClick = { onTabSelected(NavTab.SCAN) }
                    )
                    .testTag("nav_scan_button"),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = 44.dp)
                        .size(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nav_scan),
                        contentDescription = "Scan",
                        tint = if (isScanSelected) MintActivePill else FloralWhite,
                        modifier = Modifier
                            .size(28.dp)
                            .offset(y = if (isScanSelected) scanBounce.dp else 0.dp)
                    )

                    // Visible up-and-down scanning laser beam
                    if (isScanSelected) {
                        Box(
                            modifier = Modifier
                                .offset(y = scanLaserOffset.dp)
                                .size(width = 28.dp, height = 2.5.dp)
                                .clip(CircleShape)
                                .background(MintActivePill)
                        )
                    }
                }

                Text(
                    text = "Scan",
                    fontSize = 12.sp,
                    fontWeight = if (isScanSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isScanSelected) MintActivePill else FloralWhite,
                    modifier = Modifier.offset(y = 74.dp)
                )
            }

            // TAB 2: HOME (Fixed concentric cradle with elevated circular button)
            val isHomeSelected = currentTab == NavTab.HOME
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Tab,
                        onClick = { onTabSelected(NavTab.HOME) }
                    )
                    .testTag("nav_home_button"),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = 6.dp)
                        .size(60.dp)
                        .shadow(
                            elevation = if (isHomeSelected) 8.dp else 2.dp,
                            shape = CircleShape,
                            ambientColor = DarkEmerald.copy(alpha = 0.45f),
                            spotColor = DarkEmerald.copy(alpha = 0.45f)
                        )
                        .clip(CircleShape)
                        .background(if (isHomeSelected) MintActivePill else Color(0x35F6F2EA)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Home,
                        contentDescription = "Home",
                        tint = if (isHomeSelected) DarkCoffee else FloralWhite,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = "Home",
                    fontSize = 12.sp,
                    fontWeight = if (isHomeSelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isHomeSelected) MintActivePill else FloralWhite,
                    modifier = Modifier.offset(y = 74.dp)
                )
            }

            // TAB 3: HISTORY (Continuous circular rotation animation, NO circle transfer)
            val isHistorySelected = currentTab == NavTab.HISTORY
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        role = Role.Tab,
                        onClick = { onTabSelected(NavTab.HISTORY) }
                    )
                    .testTag("nav_history_button"),
                contentAlignment = Alignment.TopCenter
            ) {
                Box(
                    modifier = Modifier
                        .offset(y = 44.dp)
                        .size(36.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_nav_history),
                        contentDescription = "History",
                        tint = if (isHistorySelected) MintActivePill else FloralWhite,
                        modifier = Modifier
                            .size(28.dp)
                            .rotate(if (isHistorySelected) historyRotation else 0f)
                    )
                }

                Text(
                    text = "History",
                    fontSize = 12.sp,
                    fontWeight = if (isHistorySelected) FontWeight.Bold else FontWeight.Normal,
                    color = if (isHistorySelected) MintActivePill else FloralWhite,
                    modifier = Modifier.offset(y = 74.dp)
                )
            }
        }
    }
}
