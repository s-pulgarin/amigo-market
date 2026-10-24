package co.edu.amigo.market.ui.screens

import androidx.compose.runtime.Composable
import co.edu.amigo.market.ui.components.StubScreen

// TODO: implementar LoginScreen
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    StubScreen("Iniciar sesión", "Entrar" to onLoginSuccess)
}
