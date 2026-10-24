package co.edu.amigo.market.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.MarketRepository
import co.edu.amigo.market.data.model.CartItem
import co.edu.amigo.market.data.model.Order
import co.edu.amigo.market.data.model.PaymentMethod
import co.edu.amigo.market.data.model.Product
import java.util.UUID

class CartViewModel(private val repo: MarketRepository = InMemoryRepository) : ViewModel() {

    val items = mutableStateListOf<CartItem>()
    val total: Int get() = items.sumOf { it.product.price * it.quantity }
    val count: Int get() = items.sumOf { it.quantity }

    /** Suma 1 si el producto ya está en el carrito; si no, lo agrega con cantidad 1. */
    fun add(product: Product) {
        val index = items.indexOfFirst { it.product.id == product.id }
        if (index >= 0) {
            val current = items[index]
            items[index] = current.copy(quantity = current.quantity + 1)
        } else {
            items.add(CartItem(product, 1))
        }
    }

    /** Resta 1 y elimina el ítem al llegar a 0. */
    fun decrease(productId: String) {
        val index = items.indexOfFirst { it.product.id == productId }
        if (index < 0) return
        val current = items[index]
        if (current.quantity <= 1) {
            items.removeAt(index)
        } else {
            items[index] = current.copy(quantity = current.quantity - 1)
        }
    }

    /** Elimina el producto del carrito sin importar su cantidad. */
    fun remove(productId: String) {
        items.removeAll { it.product.id == productId }
    }

    /**
     * Devuelve null si el carrito está vacío. Si no, crea la Order,
     * la guarda con repo.placeOrder y vacía el carrito.
     */
    fun checkout(payment: PaymentMethod): Order? {
        if (items.isEmpty()) return null
        val order = Order(
            id = UUID.randomUUID().toString(),
            items = items.toList(), // copia: el carrito se vacía justo después
            total = total,
            payment = payment,
            createdAt = System.currentTimeMillis()
        )
        repo.placeOrder(order)
        items.clear()
        return order
    }
}