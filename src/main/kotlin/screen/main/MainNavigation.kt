package screen.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import screen.home.HomeScreen

/**
 *  @author pickjob@126.com
 *  @date 2024-07-24
 **/
fun NavGraphBuilder.buildMainDestination() {
    composable(
        route = MainRouter.Home.name,
    ) {
        HomeScreen()
    }
}