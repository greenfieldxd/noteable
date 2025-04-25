package greenfieldxd.noteable.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

val noteableLightColorScheme = lightColorScheme(
    primary = Color(0xFF4CAF50),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFC8E6C9),
    onPrimaryContainer = Color(0xFF1B5E20),

    secondary = Color(0xFF03A9F4),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFB3E5FC),
    onSecondaryContainer = Color(0xFF01579B),

    tertiary = Color(0xFFFF9800),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFFBF360C),

    background = Color(0xFFFFFBFA),
    onBackground = Color(0xFF212121),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF212121),

    error = Color(0xFFB00020),
    onError = Color.White,
)

val noteableDarkColorScheme = darkColorScheme(
    primary = Color(0xFF81C784),
    onPrimary = Color.Black,
    primaryContainer = Color(0xFF388E3C),
    onPrimaryContainer = Color.White,

    secondary = Color(0xFF4FC3F7),
    onSecondary = Color.Black,
    secondaryContainer = Color(0xFF0288D1),
    onSecondaryContainer = Color.White,

    tertiary = Color(0xFFFFB74D),
    onTertiary = Color.Black,
    tertiaryContainer = Color(0xFFF57C00),
    onTertiaryContainer = Color.White,

    background = Color(0xFF121212),
    onBackground = Color.White,

    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,

    error = Color(0xFFCF6679),
    onError = Color.Black,
)

@Composable
fun NoteableTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> noteableDarkColorScheme
        else -> noteableLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}

object NoteColors {
    val Yellow = Color(0xFFFFF9C4)
    val Blue = Color(0xFFBBDEFB)
    val Green = Color(0xFFC8E6C9)
    val Pink = Color(0xFFF8BBD0)
    val Purple = Color(0xFFD1C4E9)
    val Orange = Color(0xFFFFE0B2)
    val Red = Color(0xFFFFCDD2)

    val All = listOf(Yellow, Blue, Green, Pink, Purple, Orange, Red)
}