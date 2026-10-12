package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
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
        // 1. Scan animation: STRICTLY ONE-TIME up-and-down sweep when Scan is selected
        val isScanSelected = currentTab == NavTab.SCAN
        val scanLaserOffset = remember { Animatable(0f) }
        val scanLaserAlpha = remember { Animatable(0f) }
        val scanBounce = remember { Animatable(0f) }

        LaunchedEffect(isScanSelected) {
            if (isScanSelected) {
                scanLaserAlpha.snapTo(1f)
                scanLaserOffset.snapTo(-12f)
                scanBounce.snapTo(0f)

                // One-time sweep: down and back up
                scanBounce.animateTo(-4f, tween(350, easing = FastOutSlowInEasing))
                scanLaserOffset.animateTo(12f, tween(350, easing = FastOutSlowInEasing))

                scanBounce.animateTo(0f, tween(350, easing = FastOutSlowInEasing))
                scanLaserOffset.animateTo(-12f, tween(350, easing = FastOutSlowInEasing))

                // Fade out laser after single sweep completes
                scanLaserAlpha.animateTo(0f, tween(200))
            } else {
                scanLaserAlpha.snapTo(0f)
                scanLaserOffset.snapTo(0f)
                scanBounce.snapTo(0f)
            }
        }

        // 2. History animation: EXACTLY ONE-TIME 360-degree rotation of ONLY the clock
        val isHistorySelected = currentTab == NavTab.HISTORY
        val historyClockRotation = remember { Animatable(0f) }

        LaunchedEffect(isHistorySelected) {
            if (isHistorySelected) {
                historyClockRotation.snapTo(0f)
                historyClockRotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutSlowInEasing
                    )
                )
            } else {
                historyClockRotation.snapTo(0f)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(98.dp),
            verticalAlignment = Alignment.Top
        ) {
            // TAB 1: SCAN (One-time up-and-down laser scanning animation, NO circle transfer)
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
                            .offset(y = scanBounce.value.dp)
                    )

                    // One-time up-and-down scanning laser beam
                    if (scanLaserAlpha.value > 0.05f) {
                        Box(
                            modifier = Modifier
                                .offset(y = scanLaserOffset.value.dp)
                                .size(width = 28.dp, height = 2.5.dp)
                                .clip(CircleShape)
                                .background(MintActivePill.copy(alpha = scanLaserAlpha.value))
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

            // TAB 2: HOME (Fixed concentric cradle with elevated circular button that NEVER disappears)
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
                            elevation = if (isHomeSelected) 8.dp else 4.dp,
                            shape = CircleShape,
                            ambientColor = DarkEmerald.copy(alpha = 0.5f),
                            spotColor = DarkEmerald.copy(alpha = 0.5f)
                        )
                        .clip(CircleShape)
                        .background(if (isHomeSelected) MintActivePill else DarkEmerald)
                        .then(
                            if (!isHomeSelected) {
                                Modifier.border(1.5.dp, FloralWhite.copy(alpha = 0.6f), CircleShape)
                            } else {
                                Modifier
                            }
                        ),
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

            // TAB 3: HISTORY (Stationary document, ONLY the clock in corner rotates 360° ONE TIME)
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
                    Box(
                        modifier = Modifier.size(28.dp)
                    ) {
                        // 1. Stationary Document outline (Never rotates)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_nav_history_doc),
                            contentDescription = "History",
                            tint = if (isHistorySelected) MintActivePill else FloralWhite,
                            modifier = Modifier.fillMaxSize()
                        )

                        // 2. Clock in bottom right corner (Rotates 360 degrees ONE TIME when selected)
                        Box(
                            modifier = Modifier
                                .offset(x = 13.dp, y = 13.dp)
                                .size(14.dp)
                                .rotate(historyClockRotation.value),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_nav_history_clock),
                                contentDescription = null,
                                tint = if (isHistorySelected) MintActivePill else FloralWhite,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
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
