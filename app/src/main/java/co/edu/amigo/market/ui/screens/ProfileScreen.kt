package co.edu.amigo.market.ui.screens

import androidx.compose.runtime.Composable
import co.edu.amigo.market.ui.components.StubScreen

// TODO: implementar ProfileScreen
@Composable
fun ProfileScreen(onBack: () -> Unit, onLogout: () -> Unit) {
    StubScreen("Perfil", "Volver" to onBack, "Cerrar sesión" to onLogout)
}
