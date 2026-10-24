package co.edu.amigo.market.data.model

enum class Category(val label: String) {
    COMIDA("Comida"), PAPELERIA("Papelería"), LIBROS("Libros"),
    TECNOLOGIA("Tecnología"), SERVICIOS("Servicios"), TIENDA("Tienda Amigó")
}

data class Business(
    val id: String, val name: String, val category: Category,
    val location: String,          // "Bloque 2, piso 1"
    val schedule: String,          // "Lun-Vie 7:00-18:00"
    val description: String,
    val acceptsReservations: Boolean,
    val rating: Double
)

data class Product(
    val id: String, val businessId: String, val name: String,
    val price: Int,                // en pesos colombianos
    val description: String, val available: Boolean = true
)

data class CartItem(val product: Product, val quantity: Int)

enum class PaymentMethod(val label: String) {
    EFECTIVO("Efectivo al recoger"), TARJETA("Tarjeta"), PSE("PSE")
}

data class Order(
    val id: String, val items: List<CartItem>, val total: Int,
    val payment: PaymentMethod, val createdAt: Long, val status: String = "Recibido"
)

data class Reservation(
    val id: String, val businessId: String, val businessName: String,
    val date: String, val timeSlot: String, val people: Int, val notes: String = ""
)

data class User(val email: String, val name: String, val program: String)
