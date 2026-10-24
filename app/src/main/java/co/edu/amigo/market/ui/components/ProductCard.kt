package co.edu.amigo.market.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.model.Product

/** Tarjeta de producto para listas/grillas. TODO: imagen, rating y diseño final. */
@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Column(Modifier.padding(16.dp)) {
            Text(product.title, style = MaterialTheme.typography.titleMedium)
            Text(formatPrice(product.price), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

fun formatPrice(value: Double): String = "$" + "%,.0f".format(value).replace(',', '.')
