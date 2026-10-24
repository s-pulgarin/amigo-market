package co.edu.amigo.market.ui.screens

import androidx.compose.runtime.Composable
import co.edu.amigo.market.ui.components.StubScreen

// TODO: implementar CartScreen
@Composable
fun CartScreen(onBack: () -> Unit) {
    StubScreen("Carrito", "Volver" to onBack)
}
