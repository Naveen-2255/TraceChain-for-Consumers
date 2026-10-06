package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val TraceChainColorScheme = lightColorScheme(
    primary = DarkEmerald,
    onPrimary = Ivory,
    primaryContainer = Honeydew,
    onPrimaryContainer = DarkEmerald,
    secondary = Emerald,
    onSecondary = DarkEmerald,
    secondaryContainer = HoneydewMuted,
    onSecondaryContainer = DarkEmerald,
    tertiary = Emerald,
    onTertiary = DarkEmerald,
    background = Ivory,
    onBackground = Graphite,
    surface = LightSurface,
    onSurface = Graphite,
    surfaceVariant = Honeydew,
    onSurfaceVariant = Graphite,
    outline = BorderColor,
    outlineVariant = BorderColor,
    error = ErrorRed,
    onError = LightSurface,
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = TraceChainColorScheme,
        typography = Typography,
        content = content
    )
}
