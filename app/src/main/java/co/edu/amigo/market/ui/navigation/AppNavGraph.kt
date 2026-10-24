package co.edu.amigo.market.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.edu.amigo.market.ui.components.AppBottomBar
import co.edu.amigo.market.ui.screens.BusinessDetailScreen
import co.edu.amigo.market.ui.screens.BusinessListScreen
import co.edu.amigo.market.ui.screens.CartScreen
import co.edu.amigo.market.ui.screens.CheckoutScreen
import co.edu.amigo.market.ui.screens.HomeScreen
import co.edu.amigo.market.ui.screens.LoginScreen
import co.edu.amigo.market.ui.screens.MyReservationsScreen
import co.edu.amigo.market.ui.screens.OrderHistoryScreen
import co.edu.amigo.market.ui.screens.ProfileScreen
import co.edu.amigo.market.ui.screens.ReservationScreen
import co.edu.amigo.market.ui.screens.SearchScreen
import co.edu.amigo.market.viewmodel.CartViewModel
import co.edu.amigo.market.viewmodel.SessionViewModel

@Composable
fun AppNavGraph() {
    val nav = rememberNavController()
    val cartVm: CartViewModel = viewModel()
    val sessionVm: SessionViewModel = viewModel()
    val current = nav.currentBackStackEntryAsState().value?.destination?.route
    val toDetail: (String) -> Unit = { nav.navigate(Routes.businessDetail(it)) }

    Scaffold(bottomBar = {
        if (current != Routes.LOGIN) AppBottomBar(current, cartVm.count) { route ->
            nav.navigate(route) { popUpTo(Routes.HOME); launchSingleTop = true }
        }
    }) { padding ->
        NavHost(nav, startDestination = Routes.LOGIN, modifier = Modifier.padding(padding)) {
            composable(Routes.LOGIN) {
                LoginScreen(sessionVm) {
                    nav.navigate(Routes.HOME) { popUpTo(Routes.LOGIN) { inclusive = true } }
                }
            }
            composable(Routes.HOME) {
                HomeScreen(toDetail, { nav.navigate(Routes.BUSINESSES) }, { nav.navigate(Routes.SEARCH) })
            }
            composable(Routes.BUSINESSES) { BusinessListScreen(toDetail) }
            composable(Routes.BUSINESS_DETAIL) { entry ->
                val id = entry.arguments?.getString("businessId").orEmpty()
                BusinessDetailScreen(id, cartVm,
                    onReserve = { nav.navigate(Routes.reserve(id)) },
                    onGoToCart = { nav.navigate(Routes.CART) },
                    onBack = { nav.popBackStack() })
            }
            composable(Routes.CART) { CartScreen(cartVm) { nav.navigate(Routes.CHECKOUT) } }
            composable(Routes.CHECKOUT) {
                CheckoutScreen(cartVm) { nav.navigate(Routes.ORDERS) { popUpTo(Routes.HOME) } }
            }
            composable(Routes.RESERVE) { entry ->
                ReservationScreen(entry.arguments?.getString("businessId").orEmpty(),
                    onDone = { nav.navigate(Routes.MY_RESERVATIONS) { popUpTo(Routes.HOME) } })
            }
            composable(Routes.MY_RESERVATIONS) { MyReservationsScreen() }
            composable(Routes.SEARCH) { SearchScreen(cartVm, toDetail) }
            composable(Routes.PROFILE) {
                ProfileScreen(sessionVm,
                    onOpenOrders = { nav.navigate(Routes.ORDERS) },
                    onOpenReservations = { nav.navigate(Routes.MY_RESERVATIONS) },
                    onLogout = {
                        sessionVm.logout()
                        nav.navigate(Routes.LOGIN) { popUpTo(nav.graph.id) { inclusive = true } }
                    })
            }
            composable(Routes.ORDERS) { OrderHistoryScreen() }
        }
    }
}
