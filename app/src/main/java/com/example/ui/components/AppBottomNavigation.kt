package com.example.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
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
    // Horizontal center fraction for each tab
    val targetFraction = when (currentTab) {
        NavTab.SCAN -> 1f / 6f
        NavTab.HOME -> 3f / 6f
        NavTab.HISTORY -> 5f / 6f
    }

    val animatedFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = spring(
            dampingRatio = 0.78f,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "cradle_cx"
    )

    // Outer Box: Exact concentric circular arc cradle and fillets matching reference design
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val topY = 32.dp.toPx()
                val cornerR = 20.dp.toPx()

                // Floating circular button geometry
                val btnDiameter = 60.dp.toPx()
                val btnRadius = btnDiameter / 2f
                val cradleMargin = 6.dp.toPx()          // Exact 6dp uniform concentric gap
                val rCradle = btnRadius + cradleMargin  // 36dp concave cradle radius
                val rShoulder = 14.dp.toPx()            // 14dp convex shoulder flare
                val cy = topY + 4.dp.toPx()             // Concentric center shared with button (36dp from top)

                val Ys = topY + rShoulder
                val deltaY = Ys - cy
                val sumR = rCradle + rShoulder
                val deltaX = sqrt((sumR * sumR) - (deltaY * deltaY))

                val sinBeta = (deltaY / sumR).coerceIn(-1f, 1f)
                val betaDeg = Math.toDegrees(asin(sinBeta.toDouble())).toFloat()

                // Safely clamp cx so cradle never collides with outer corners
                val minCx = deltaX + cornerR + 2.dp.toPx()
                val maxCx = size.width - deltaX - cornerR - 2.dp.toPx()
                val cx = (size.width * animatedFraction).coerceIn(minCx, maxCx)

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

                    // Down to bottom-right of screen, across bottom, and close
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }

                drawPath(path = path, color = DarkEmerald)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(98.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Tab 1: SCAN
            NavBarTabItem(
                selected = currentTab == NavTab.SCAN,
                title = "Scan",
                iconPainterRes = R.drawable.ic_nav_scan,
                testTag = "nav_scan_button",
                onClick = { onTabSelected(NavTab.SCAN) },
                modifier = Modifier.weight(1f)
            )

            // Tab 2: HOME
            NavBarTabItem(
                selected = currentTab == NavTab.HOME,
                title = "Home",
                iconVector = Icons.Filled.Home,
                testTag = "nav_home_button",
                onClick = { onTabSelected(NavTab.HOME) },
                modifier = Modifier.weight(1f)
            )

            // Tab 3: HISTORY
            NavBarTabItem(
                selected = currentTab == NavTab.HISTORY,
                title = "History",
                iconPainterRes = R.drawable.ic_nav_history,
                testTag = "nav_history_button",
                onClick = { onTabSelected(NavTab.HISTORY) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NavBarTabItem(
    selected: Boolean,
    title: String,
    testTag: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconVector: ImageVector? = null,
    iconPainterRes: Int? = null
) {
    // Dynamic pop-up circular button animations
    val buttonSize by animateDpAsState(
        targetValue = if (selected) 60.dp else 36.dp,
        animationSpec = spring(dampingRatio = 0.72f, stiffness = Spring.StiffnessMediumLow),
        label = "tab_button_size"
    )

    // When selected: sits at 6dp (center at 36dp, perfectly concentric with the cradle)
    // When inactive: sits comfortably down at 42dp inside the solid green bar
    val buttonOffsetY by animateDpAsState(
        targetValue = if (selected) 6.dp else 42.dp,
        animationSpec = spring(dampingRatio = 0.72f, stiffness = Spring.StiffnessMediumLow),
        label = "tab_offset_y"
    )

    val iconSize by animateDpAsState(
        targetValue = if (selected) 30.dp else 24.dp,
        animationSpec = spring(dampingRatio = 0.72f, stiffness = Spring.StiffnessMediumLow),
        label = "tab_icon_size"
    )

    val shadowElevation by animateDpAsState(
        targetValue = if (selected) 8.dp else 0.dp,
        label = "tab_shadow"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Tab,
                onClick = onClick
            )
            .testTag(testTag),
        contentAlignment = Alignment.TopCenter
    ) {
        // Floating circular button (concentric with the cradle cutout)
        Box(
            modifier = Modifier
                .offset(y = buttonOffsetY)
                .size(buttonSize)
                .shadow(
                    elevation = shadowElevation,
                    shape = CircleShape,
                    ambientColor = DarkEmerald.copy(alpha = 0.45f),
                    spotColor = DarkEmerald.copy(alpha = 0.45f)
                )
                .clip(CircleShape)
                .background(if (selected) MintActivePill else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            val iconTint = if (selected) DarkCoffee else FloralWhite

            if (iconVector != null) {
                Icon(
                    imageVector = iconVector,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            } else if (iconPainterRes != null) {
                Icon(
                    painter = painterResource(id = iconPainterRes),
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }
        }

        // Title Label: placed evenly at 74dp baseline for all tabs
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) MintActivePill else FloralWhite,
            modifier = Modifier.offset(y = 74.dp)
        )
    }
}
