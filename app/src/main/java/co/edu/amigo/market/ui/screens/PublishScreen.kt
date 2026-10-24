package co.edu.amigo.market.ui.screens

import androidx.compose.runtime.Composable
import co.edu.amigo.market.ui.components.StubScreen

// TODO: implementar PublishScreen
@Composable
fun PublishScreen(onDone: () -> Unit) {
    StubScreen("Publicar producto", "Listo" to onDone)
}
