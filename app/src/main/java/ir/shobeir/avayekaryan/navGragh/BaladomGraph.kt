package ir.shobeir.avayekaryan.navGragh


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.shobeir.avayekaryan.CommentScreen
import ir.shobeir.avayekaryan.SharedViewModel
import ir.shobeir.avayekaryan.details.DetailsScreen
import ir.shobeir.avayekaryan.gallery.DetailsGallery
import ir.shobeir.avayekaryan.gallery.GalleryScreen
import ir.shobeir.avayekaryan.home.HomeScreen


@Composable
fun BaladomNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.route,
) {
    val sharedViewModel: SharedViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, sharedViewModel = sharedViewModel)
        }

        composable(route = Screen.Detail.route)
        {
            DetailsScreen(sharedViewModel = sharedViewModel, navController = navController)
        }

        composable(route = Screen.Gallery.route) {
            GalleryScreen(sharedViewModel = sharedViewModel, navController = navController)
        }

        composable(route = Screen.DetailGallery.route)
        {
            DetailsGallery(sharedViewModel = sharedViewModel)
        }

        composable(
            route = Screen.Comments.route + "/{idPost}",
            arguments = listOf(
                navArgument("idPost") {
                    type = NavType.StringType
                })
        ) {
            it.arguments?.getString("idPost")?.let { id ->
                CommentScreen(idPost = id)
            }

        }

    }
}