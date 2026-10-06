package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// =========================================================================
// 5-Color Palette: Pitch Black, Dark Coffee, Floral White, Almond Cream, Dusty Olive
// =========================================================================
val PitchBlack = Color(0xFF1B1713)
val DarkCoffee = Color(0xFF3E2B22)
val FloralWhite = Color(0xFFF6F2EA)
val AlmondCream = Color(0xFFEADDD0)
val DustyOlive = Color(0xFF6B734A) // Exactly #6B734A from the uploaded palette

// Component & Token Mappings
val Ivory = FloralWhite
val Honeydew = Color(0xFFE8ECD9) // Pale olive tint derived from #6B734A + #F6F2EA
val HoneydewMuted = Color(0xFFF1F4E9)
val DarkEmerald = DustyOlive // #6B734A (Navigation bar, primary actions, avatar)
val BrandChainTeal = DustyOlive // #6B734A ("Chain" in brand logo)
val Emerald = DustyOlive
val MintActivePill = AlmondCream // #EADDD0 (Warm, rich Almond Cream active button)
val MintBadgeBg = Color(0xFFE4E9D8) // Soft olive pill background
val MintBadgeText = Color(0xFF434C2C) // Deep olive text for verified badge
val AmberBadgeBg = Color(0xFFF4ECE2) // Soft Almond Cream badge background
val AmberBadgeText = DarkCoffee // #3E2B22 (Dark Coffee text for expiry badge)
val WarningAmber = DarkCoffee
val Graphite = PitchBlack // #1B1713 (Pitch Black primary typography)
val SecondaryText = Color(0xFF756A61) // Warm taupe-coffee for subtitles, batches, and secondary copy
val ChevronMuted = Color(0xFF948A80)
val LightSurface = Color(0xFFFFFFFF)
val CardBorderColor = AlmondCream // #EADDD0 (Almond Cream card borders)
val BorderColor = CardBorderColor
val TimelineConnector = Color(0xFFB0BA94) // Muted olive timeline connector
val ErrorRed = Color(0xFFC84B31) // Terracotta alert dot

// Main body gradient: Floral White down to gentle Almond Cream
val MainBodyGradient = Brush.verticalGradient(
    colors = listOf(
        FloralWhite,
        Color(0xFFF8F5EE),
        Color(0xFFF0E8DC)
    )
)
