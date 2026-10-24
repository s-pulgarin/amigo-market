package co.edu.amigo.market.data

import co.edu.amigo.market.data.model.Category
import co.edu.amigo.market.data.model.Product
import co.edu.amigo.market.data.model.User

/**
 * Datos de prueba en memoria.
 * TODO(E2): completar el catálogo (mín. 2 productos por categoría) y más vendedores.
 */
object SeedData {

    val users = listOf(
        User(id = "u1", name = "Laura Gómez", email = "laura@amigo.edu.co", program = "Ingeniería de Sistemas"),
        User(id = "u2", name = "Carlos Ruiz", email = "carlos@amigo.edu.co", program = "Diseño Gráfico")
    )

    val products = listOf(
        Product(
            id = "p1",
            title = "Cálculo de Stewart",
            description = "Libro usado en buen estado, 8va edición.",
            price = 60000.0,
            category = Category.BOOKS,
            sellerId = "u1"
        ),
        Product(
            id = "p2",
            title = "Audífonos Bluetooth",
            description = "Poco uso, con estuche de carga.",
            price = 85000.0,
            category = Category.TECHNOLOGY,
            sellerId = "u2"
        ),
        Product(
            id = "p3",
            title = "Brownies caseros",
            description = "Paquete x3, entrega en la cafetería.",
            price = 8000.0,
            category = Category.FOOD,
            sellerId = "u1",
            stock = 20
        )
    )
}
