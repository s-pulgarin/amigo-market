
package co.edu.amigo.market.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.model.Product
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductCard(
    product: Product,
    onAddToCart: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    val priceFormat = NumberFormat.getNumberInstance(
        Locale.forLanguageTag("es-CO")
    ).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "$ ${priceFormat.format(product.price)}",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!product.available) {
                    Text(
                        text = "Agotado",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }

                Button(
                    onClick = { onAddToCart(product) },
                    enabled = product.available
                ) {
                    Text("Agregar")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Producto disponible")
@Composable
private fun ProductCardAvailablePreview() {
    MaterialTheme {
        ProductCard(
            product = Product(
                id = "1",
                businessId = "cafeteria",
                name = "Empanada",
                price = 2500,
                description = "Empanada de carne recién preparada",
                available = true
            ),
            onAddToCart = {}
        )
    }
}

@Preview(showBackground = true, name = "Producto agotado")
@Composable
private fun ProductCardUnavailablePreview() {
    MaterialTheme {
        ProductCard(
            product = Product(
                id = "2",
                businessId = "cafeteria",
                name = "Jugo natural",
                price = 4000,
                description = "Jugo natural de mango",
                available = false
            ),
            onAddToCart = {}
        )
    }
}
