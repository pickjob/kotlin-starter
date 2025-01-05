package theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.materialkolor.PaletteStyle
import com.materialkolor.rememberDynamicColorScheme

/**
 *  @author pickjob@126.com
 *  @date 2024-07-06
 **/
@Composable
fun KolorTheme(seedColor: Color, content: @Composable () -> Unit) {

    val colorScheme = rememberDynamicColorScheme(
        seedColor = seedColor,
        isDark = true,
        isAmoled = true,
        style = PaletteStyle.Content,
        isExtendedFidelity = true,
    )

    MaterialTheme(
        colorScheme = colorScheme,
        shapes = shapes,
        typography = Typography(
            displaySmall = MaterialTheme.typography.displaySmall.copy(
                fontFamily = firaCodeFont,
            ),
            displayMedium = MaterialTheme.typography.displayMedium.copy(
                fontFamily = firaCodeFont,
            ),
            displayLarge = MaterialTheme.typography.displayLarge.copy(
                fontFamily = firaCodeFont,
            ),
            headlineSmall = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = firaCodeFont,
            ),
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = firaCodeFont,
            ),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = firaCodeFont,
            ),
            titleSmall = MaterialTheme.typography.titleSmall.copy(
                fontFamily = firaCodeFont,
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(
                fontFamily = firaCodeFont,
            ),
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontFamily = firaCodeFont,
            ),
            bodySmall = MaterialTheme.typography.bodySmall.copy(
                fontFamily = firaCodeFont,
            ),
            bodyMedium = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = firaCodeFont,
            ),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = firaCodeFont,
            ),
            labelSmall = MaterialTheme.typography.labelSmall.copy(
                fontFamily = firaCodeFont,
            ),
            labelMedium = MaterialTheme.typography.labelMedium.copy(
                fontFamily = firaCodeFont,
            ),
            labelLarge = MaterialTheme.typography.labelLarge.copy(
                fontFamily = firaCodeFont,
            )
        ),
    ) {
        content()
    }
}