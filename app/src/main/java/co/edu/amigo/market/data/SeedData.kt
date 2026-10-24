package co.edu.amigo.market.data

import co.edu.amigo.market.data.model.Business
import co.edu.amigo.market.data.model.Category
import co.edu.amigo.market.data.model.Product

object SeedData {
    val businesses = listOf(
        Business("cafe", "Cafetería Central", Category.COMIDA, "Bloque 1, piso 1",
            "Lun-Sáb 6:30-20:00", "Café, desayunos y snacks", true, 4.5),
        Business("copias", "Fotocopiadora Amigó", Category.SERVICIOS, "Bloque 3, piso 2",
            "Lun-Vie 7:00-19:00", "Impresiones, copias y anillados", true, 4.2)
    )
    val products = listOf(
        Product("p1", "cafe", "Tinto grande", 2500, "Café colombiano 12 oz"),
        Product("p2", "cafe", "Empanada", 3000, "De carne, recién hecha"),
        Product("p3", "copias", "Impresión B/N", 200, "Por hoja, tamaño carta")
    )
}
