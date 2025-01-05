// NavHost: 包含当前导航目的地的界面元素
// NavGraph: 一种数据结构，用于定义应用中的所有导航目的地以及它们如何连接在一起
// NavController: 用于管理目的地之间导航的中央协调器
//      val navController = rememberNavController()
//      NavController.navigate()
//      NavController.navigateUp()
//      NavController.popBackStack()
// NavDestination: 导航图中的节点
//      Hosted
//          Fills the entire navigation host.
//      Dialog
//          Presents overlay UI components.
// Route: 唯一标识目的地及其所需的任何数据

val navController = rememberNavController()
NavHost(navController = navController, startDestination = Profile) {
    composable<Profile> { ProfileScreen( /* ... */ ) }
    composable<FriendsList> { FriendsListScreen( /* ... */ ) }
    // Add more destinations similarly.
}

val navGraph by remember(navController) {
    navController.createGraph(startDestination = Profile)) {
        composable<Profile> { ProfileScreen( /* ... */ ) }
        composable<FriendsList> { FriendsListScreen( /* ... */ ) }
    }
}
NavHost(navController, navGraph)

@Serializable
data class Profile(val name: String)

@Serializable
object FriendsList

// Define the ProfileScreen composable.
@Composable
fun ProfileScreen(
    profile: Profile
    onNavigateToFriendsList: () -> Unit,
) {
    Text("Profile for ${profile.name}")
    Button(onClick = { onNavigateToFriendsList() }) {
        Text("Go to Friends List")
    }
}

// Define the FriendsListScreen composable.
@Composable
fun FriendsListScreen(onNavigateToProfile: () -> Unit) {
    Text("Friends List")
    Button(onClick = { onNavigateToProfile() }) {
        Text("Go to Profile")
    }
}

// Define the MyApp composable, including the `NavController` and `NavHost`.
@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Profile(name = "John Smith")) {
        composable<Profile> { backStackEntry ->
            val profile: Profile = backStackEntry.toRoute()
            ProfileScreen(
                profile = profile,
                onNavigateToFriendsList = {
                    navController.navigate(route = FriendsList)
                }
            )
        }
        composable<FriendsList> {
            FriendsListScreen(
                onNavigateToProfile = {
                    navController.navigate(
                        route = Profile(name = "Aisha Devi")
                    )
                }
            )
        }
        dialog<Settings> { SettingsScreen() }
    }
}

fun NavGraphBuilder.contactDetailsScreen() {
    composable<ContactDetails> { navBackStackEntry ->
        ContactDetailsScreen(contact = navBackStackEntry.toRoute())
    }
}