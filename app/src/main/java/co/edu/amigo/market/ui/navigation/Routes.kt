package co.edu.amigo.market.ui.navigation

/** Rutas de navegación de la app. */
sealed class Routes(val route: String) {
    data object Login : Routes("login")
    data object Home : Routes("home")
    data object Cart : Routes("cart")
    data object Profile : Routes("profile")
    data object Publish : Routes("publish")

    data object ProductDetail : Routes("product/{$ARG_PRODUCT_ID}") {
        fun create(productId: String) = "product/$productId"
    }

    companion object {
        const val ARG_PRODUCT_ID = "productId"
    }
}
