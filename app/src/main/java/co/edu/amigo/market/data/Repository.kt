package co.edu.amigo.market.data

import co.edu.amigo.market.data.model.CartItem
import co.edu.amigo.market.data.model.Category
import co.edu.amigo.market.data.model.Order
import co.edu.amigo.market.data.model.Product
import co.edu.amigo.market.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

/**
 * Fuente única de datos de la app. Por ahora es en memoria, cargada desde [SeedData].
 * Los ViewModels solo deben hablar con este objeto.
 */
object Repository {

    private val _products = MutableStateFlow(SeedData.products)
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _users = MutableStateFlow(SeedData.users)
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _cart = MutableStateFlow<List<CartItem>>(emptyList())
    val cart: StateFlow<List<CartItem>> = _cart.asStateFlow()

    private val _orders = MutableStateFlow<List<Order>>(emptyList())
    val orders: StateFlow<List<Order>> = _orders.asStateFlow()

    // --- Productos ---

    fun getProduct(id: String): Product? = _products.value.find { it.id == id }

    fun productsByCategory(category: Category): List<Product> =
        _products.value.filter { it.category == category }

    fun search(query: String): List<Product> =
        _products.value.filter {
            it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
        }

    fun addProduct(product: Product) {
        _products.update { it + product }
    }

    // --- Usuarios / sesión ---

    fun getUser(id: String): User? = _users.value.find { it.id == id }

    fun login(email: String): Boolean {
        val user = _users.value.find { it.email.equals(email, ignoreCase = true) }
        _currentUser.value = user
        return user != null
    }

    fun logout() {
        _currentUser.value = null
        _cart.value = emptyList()
    }

    // --- Carrito ---

    fun addToCart(product: Product, quantity: Int = 1) {
        _cart.update { items ->
            val existing = items.find { it.product.id == product.id }
            if (existing == null) items + CartItem(product, quantity)
            else items.map {
                if (it.product.id == product.id) it.copy(quantity = it.quantity + quantity) else it
            }
        }
    }

    fun updateQuantity(productId: String, quantity: Int) {
        _cart.update { items ->
            if (quantity <= 0) items.filterNot { it.product.id == productId }
            else items.map { if (it.product.id == productId) it.copy(quantity = quantity) else it }
        }
    }

    fun removeFromCart(productId: String) = updateQuantity(productId, 0)

    fun clearCart() {
        _cart.value = emptyList()
    }

    // --- Pedidos ---

    fun checkout(): Order? {
        val buyer = _currentUser.value ?: return null
        val items = _cart.value
        if (items.isEmpty()) return null
        val order = Order(id = UUID.randomUUID().toString(), buyerId = buyer.id, items = items)
        _orders.update { it + order }
        clearCart()
        return order
    }
}
