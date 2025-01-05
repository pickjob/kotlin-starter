package component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 *  @author pickjob@126.com
 *  @date 2024-07-24
 **/
@Composable
fun CenterArrangeNavigationRail(
    modifier: Modifier = Modifier,
    containerColor: Color = NavigationRailDefaults.ContainerColor,
    contentColor: Color = contentColorFor(containerColor),
    windowInsets: WindowInsets = NavigationRailDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier,
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .windowInsetsPadding(windowInsets)
                .widthIn(min = 80.0.dp)
                .padding(vertical = 4.dp)
                .selectableGroup(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}