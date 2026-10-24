package co.edu.amigo.market.data

import co.edu.amigo.market.data.model.Business
import co.edu.amigo.market.data.model.Category
import co.edu.amigo.market.data.model.Product

// Datos de ejemplo: 8 negocios y sus productos (precios en COP)
object SeedData {
    val businesses = listOf(
        Business("cafe", "Cafetería Central", Category.COMIDA, "Bloque 1, piso 1",
            "Lun-Sáb 6:30-20:00", "Café, desayunos y snacks", true, 4.5),
        Business("copias", "Fotocopiadora Amigó", Category.SERVICIOS, "Bloque 3, piso 2",
            "Lun-Vie 7:00-19:00", "Impresiones, copias y anillados", true, 4.2),
        Business("resto", "Restaurante Amigó", Category.COMIDA, "Bloque 1, piso 2",
            "Lun-Vie 11:00-15:00", "Almuerzos corrientes y platos del día", true, 4.6),
        Business("papel", "Papelería Escolar", Category.PAPELERIA, "Bloque 2, piso 1",
            "Lun-Vie 7:00-18:00", "Cuadernos, útiles y material de oficina", false, 4.1),
        Business("libros", "Librería Universitaria", Category.LIBROS, "Biblioteca, piso 1",
            "Lun-Vie 8:00-17:00", "Textos académicos y literatura", true, 4.7),
        Business("tienda", "Tienda Amigó", Category.TIENDA, "Bloque 2, piso 1",
            "Lun-Vie 8:00-17:00", "Souvenirs y ropa institucional", false, 4.3),
        Business("fruta", "Frutería Natural", Category.COMIDA, "Patio central",
            "Lun-Sáb 8:00-16:00", "Frutas frescas, jugos y ensaladas", false, 4.4),
        Business("tecno", "Tecno Amigó", Category.TECNOLOGIA, "Bloque 4, piso 1",
            "Lun-Vie 9:00-17:00", "Accesorios y electrónica", false, 4.0)
    )

    val products = listOf(
        // Cafetería
        Product("p1", "cafe", "Tinto grande", 2500, "Café colombiano 12 oz"),
        Product("p2", "cafe", "Empanada", 3000, "De carne, recién hecha"),
        Product("p4", "cafe", "Sándwich de pollo", 7500, "Con queso y vegetales"),
        Product("p5", "cafe", "Jugo natural", 4500, "Mora, lulo o maracuyá"),
        Product("p6", "cafe", "Brownie", 3500, "Con chocolate oscuro"),
        // Fotocopiadora
        Product("p3", "copias", "Impresión B/N", 200, "Por hoja, tamaño carta"),
        Product("p7", "copias", "Impresión a color", 800, "Por hoja, tamaño carta"),
        Product("p8", "copias", "Anillado", 5000, "Hasta 100 hojas"),
        Product("p9", "copias", "Escaneo", 500, "Por documento, envío al correo"),
        // Restaurante
        Product("p10", "resto", "Almuerzo ejecutivo", 12000, "Sopa, seco y jugo"),
        Product("p11", "resto", "Bandeja paisa", 16000, "Plato típico completo"),
        Product("p12", "resto", "Sopa del día", 6000, "Porción individual"),
        Product("p13", "resto", "Jugo de mora", 4000, "Vaso de 12 oz"),
        Product("p14", "resto", "Postre del día", 3500, "Pregunta por el sabor"),
        // Papelería
        Product("p15", "papel", "Cuaderno 100 hojas", 6500, "Cuadriculado"),
        Product("p16", "papel", "Esfero negro", 1500, "Punta media"),
        Product("p17", "papel", "Resaltadores x4", 8000, "Colores surtidos"),
        Product("p18", "papel", "Block de notas", 3000, "Hojas adhesivas"),
        Product("p19", "papel", "Carpeta oficio", 2000, "Con gancho legajador"),
        // Librería
        Product("p20", "libros", "Cálculo diferencial", 85000, "Texto guía del curso"),
        Product("p21", "libros", "Fundamentos de programación", 68000, "Lógica y algoritmos"),
        Product("p22", "libros", "Diccionario inglés-español", 45000, "Edición de bolsillo"),
        Product("p23", "libros", "Cien años de soledad", 52000, "Novela de García Márquez"),
        // Tienda institucional
        Product("p24", "tienda", "Camiseta Amigó", 45000, "Algodón, tallas S a XL"),
        Product("p25", "tienda", "Termo institucional", 38000, "Acero inoxidable 500 ml"),
        Product("p26", "tienda", "Gorra", 25000, "Con escudo bordado"),
        Product("p27", "tienda", "Llavero", 8000, "Metálico con logo"),
        Product("p28", "tienda", "Tula Amigó", 55000, "Resistente al agua"),
        // Frutería
        Product("p29", "fruta", "Ensalada de frutas", 6000, "Frutas de temporada"),
        Product("p30", "fruta", "Fresas con crema", 7000, "Vaso de 12 oz"),
        Product("p31", "fruta", "Manzana", 2000, "Unidad"),
        Product("p32", "fruta", "Jugo de naranja", 4500, "Recién exprimido"),
        // Tecnología
        Product("p33", "tecno", "Memoria USB 32 GB", 30000, "USB 3.0"),
        Product("p34", "tecno", "Cable USB-C", 15000, "1 metro"),
        Product("p35", "tecno", "Audífonos", 40000, "Con micrófono"),
        Product("p36", "tecno", "Mouse inalámbrico", 35000, "Pila incluida", available = false)
    )
}