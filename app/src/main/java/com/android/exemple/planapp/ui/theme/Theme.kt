package com.android.exemple.planapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

data class ThemeColors(
    val backgroundColor: Color,
    val textColor: Color
)

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200
)

@Composable
fun PlanAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    val themeColors = remember(darkTheme) {
        if (darkTheme) {
            ThemeColors(
                backgroundColor = Color.DarkGray,
                textColor = Color.White
            )
        } else {
            ThemeColors(
                backgroundColor = Color(0xFF444444),
                textColor = Color.Black
            )
        }
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = {
            CompositionLocalProvider(LocalThemeColors provides themeColors) {
                content()
            }
        }
    )
}

val LocalThemeColors = compositionLocalOf { ThemeColors(Color.White, Color.Black) }