
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.awaitApplication
import androidx.compose.ui.window.rememberWindowState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import base.saveable.GlobalSaveableStateRegistry
import component.CenterArrangeNavigationRail
import kotlinx.coroutines.runBlocking
import org.apache.logging.log4j.kotlin.logger
import screen.main.MainRouter
import screen.main.buildMainDestination
import theme.DeepPurple_A700
import theme.KolorTheme

@Composable
fun ComposeApp() {

    Scaffold() {
        val mainController = rememberNavController()

        var mainRouterSelected by remember { mutableIntStateOf(0) }

        Row {
            CenterArrangeNavigationRail {
                MainRouter.entries.forEachIndexed { index, item ->
                    NavigationRailItem(
                        icon = { Icon(item.icon, contentDescription = item.name) },
                        label = { Text(item.name) },
                        selected = mainRouterSelected == index,
                        onClick = { mainRouterSelected = index }
                    )
                }
            }

            NavHost(mainController, startDestination = MainRouter.Home.name) {
                buildMainDestination()
            }
        }
    }
}

fun main() = runBlocking {
    val logger = logger()

    awaitApplication {
        logger.info("正在初始化...")

        val saveableStateRegistry = remember { GlobalSaveableStateRegistry() }

        CompositionLocalProvider(LocalSaveableStateRegistry provides saveableStateRegistry) {
            KolorTheme(DeepPurple_A700) {
                val windowState = rememberWindowState()
                Window(
                    title = "KotlinStarter",
                    onCloseRequest = ::exitApplication,
                    state = windowState,
                ) {
                    ComposeApp()
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                logger.info("正在注销...")
                saveableStateRegistry.performSave()
            }
        }
    }
}
