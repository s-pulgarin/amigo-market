package co.edu.amigo.market.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.MarketRepository
import co.edu.amigo.market.data.model.CartItem
import co.edu.amigo.market.data.model.Order
import co.edu.amigo.market.data.model.PaymentMethod
import co.edu.amigo.market.data.model.Product

// B2
class CartViewModel(private val repo: MarketRepository = InMemoryRepository) : ViewModel() {
    val items = mutableStateListOf<CartItem>()
    val total: Int get() = items.sumOf { it.product.price * it.quantity }
    val count: Int get() = items.sumOf { it.quantity }
    fun add(product: Product) { /* TODO(B2) */ }
    fun decrease(productId: String) { /* TODO(B2) */ }
    fun remove(productId: String) { /* TODO(B2) */ }
    fun checkout(payment: PaymentMethod): Order? { /* TODO(B2) */ return null }
}
