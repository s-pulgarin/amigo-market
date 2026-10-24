
package co.edu.amigo.market.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.model.User

// D1: Login simulado
class SessionViewModel : ViewModel() {

    var user by mutableStateOf<User?>(null)
        private set

    fun login(email: String, password: String): Boolean {
        val cleanEmail = email.trim().lowercase()

        // Validar correo institucional y contraseña
        if (!cleanEmail.endsWith("@amigo.edu.co")) {
            return false
        }

        if (password.length < 6) {
            return false
        }

        // Generar el nombre a partir del correo
        val username = cleanEmail
            .substringBefore("@")
            .replace(Regex("[._-]+"), " ")
            .split(" ")
            .filter { it.isNotBlank() }
            .joinToString(" ") { it.replaceFirstChar { char -> char.uppercase() } }

        user = User(
            cleanEmail,
            username,
            "Desarrollo de Software"
        )

        return true
    }

    fun logout() {
        user = null
    }
}
