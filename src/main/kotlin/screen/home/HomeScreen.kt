package screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import theme.*

/**
 *  @author pickjob@126.com
 *  @date 2024-07-23
 **/
@Composable
fun HomeScreen() {
    Row (
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val rainbow = listOf(
            Red_700,
            Orange_700,
            Yellow_700,
            Green_700,
            Cyan_700,
            Blue_700,
            Purple_700,
        )
        Text(
            text = "Hello Compose Desktop!",
            style = MaterialTheme.typography.displayLarge.copy(
                brush = Brush.linearGradient(
                    colors = rainbow,
                ),
            ),
        )
    }
}