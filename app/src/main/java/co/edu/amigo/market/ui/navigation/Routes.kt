package co.edu.amigo.market.ui.navigation

object Routes {
    const val LOGIN = "login"; const val HOME = "home"; const val BUSINESSES = "businesses"
    const val BUSINESS_DETAIL = "business/{businessId}"; const val CART = "cart"
    const val CHECKOUT = "checkout"; const val RESERVE = "reserve/{businessId}"
    const val MY_RESERVATIONS = "reservations"; const val SEARCH = "search"
    const val PROFILE = "profile"; const val ORDERS = "orders"
    fun businessDetail(id: String) = "business/$id"
    fun reserve(id: String) = "reserve/$id"
}
