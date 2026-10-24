package co.edu.amigo.market.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import co.edu.amigo.market.ui.screens.CartScreen
import co.edu.amigo.market.ui.screens.HomeScreen
import co.edu.amigo.market.ui.screens.LoginScreen
import co.edu.amigo.market.ui.screens.ProductDetailScreen
import co.edu.amigo.market.ui.screens.ProfileScreen
import co.edu.amigo.market.ui.screens.PublishScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Login.route,
        modifier = modifier
    ) {
        composable(Routes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Home.route) {
            HomeScreen(
                onProductClick = { navController.navigate(Routes.ProductDetail.create(it)) },
                onCartClick = { navController.navigate(Routes.Cart.route) },
                onProfileClick = { navController.navigate(Routes.Profile.route) },
                onPublishClick = { navController.navigate(Routes.Publish.route) }
            )
        }
        composable(
            route = Routes.ProductDetail.route,
            arguments = listOf(navArgument(Routes.ARG_PRODUCT_ID) { type = NavType.StringType })
        ) { entry ->
            ProductDetailScreen(
                productId = entry.arguments?.getString(Routes.ARG_PRODUCT_ID).orEmpty(),
                onBack = { navController.popBackStack() }
            )
        }
        composable(Routes.Cart.route) {
            CartScreen(onBack = { navController.popBackStack() })
        }
        composable(Routes.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onLogout = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.Publish.route) {
            PublishScreen(onDone = { navController.popBackStack() })
        }
    }
}
