package com.yasinkacmaz.solotest

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Blue900 = Color(0xFF0D47A1)
val Yellow700 = Color(0xFFFBC02D)

private val DarkColorPalette = darkColors(
    primary = Blue900,
    primaryVariant = Blue900,
    secondary = Yellow700
)

private val LightColorPalette = lightColors(
    primary = Blue900,
    primaryVariant = Blue900,
    secondary = Yellow700
)

@Composable
fun SoloTestTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (darkTheme) DarkColorPalette else LightColorPalette,
        content = content
    )
}
