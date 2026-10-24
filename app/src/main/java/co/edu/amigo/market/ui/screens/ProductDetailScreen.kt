package co.edu.amigo.market.ui.screens

import androidx.compose.runtime.Composable
import co.edu.amigo.market.ui.components.StubScreen

// TODO: implementar ProductDetailScreen
@Composable
fun ProductDetailScreen(productId: String, onBack: () -> Unit) {
    StubScreen("Producto $productId", "Volver" to onBack)
}
