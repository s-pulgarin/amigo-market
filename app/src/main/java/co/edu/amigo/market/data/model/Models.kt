package co.edu.amigo.market.data.model

/** Categorías del catálogo de Amigo Market. */
enum class Category(val label: String) {
    BOOKS("Libros"),
    TECHNOLOGY("Tecnología"),
    FOOD("Comida"),
    SERVICES("Servicios"),
    CLOTHING("Ropa"),
    OTHER("Otros")
}

/** Estudiante/vendedor registrado en la plataforma. */
data class User(
    val id: String,
    val name: String,
    val email: String,
    val program: String = "",
    val phone: String = "",
    val avatarUrl: String? = null
)

/** Producto o servicio publicado por un vendedor. */
data class Product(
    val id: String,
    val title: String,
    val description: String,
    val price: Double,
    val category: Category,
    val sellerId: String,
    val imageUrl: String? = null,
    val stock: Int = 1,
    val rating: Float = 0f
)

/** Línea del carrito de compras. */
data class CartItem(
    val product: Product,
    val quantity: Int = 1
) {
    val subtotal: Double get() = product.price * quantity
}

enum class OrderStatus { PENDING, CONFIRMED, DELIVERED, CANCELLED }

/** Pedido confirmado a partir del carrito. */
data class Order(
    val id: String,
    val buyerId: String,
    val items: List<CartItem>,
    val status: OrderStatus = OrderStatus.PENDING,
    val createdAt: Long = System.currentTimeMillis()
) {
    val total: Double get() = items.sumOf { it.subtotal }
}
