package co.edu.amigo.market.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import co.edu.amigo.market.data.model.Product

@Composable
fun ProductCard(product: Product, onAddToCart: () -> Unit, modifier: Modifier = Modifier) {
    Text("TODO ProductCard", modifier = modifier)
}
