package co.edu.amigo.market.ui.screens

import androidx.compose.runtime.Composable
import co.edu.amigo.market.ui.components.StubScreen

// TODO: implementar HomeScreen
@Composable
fun HomeScreen(onProductClick: (String) -> Unit, onCartClick: () -> Unit, onProfileClick: () -> Unit, onPublishClick: () -> Unit) {
    StubScreen("Inicio",
        "Ver producto p1" to { onProductClick("p1") },
        "Carrito" to onCartClick,
        "Perfil" to onProfileClick,
        "Publicar" to onPublishClick)
}
