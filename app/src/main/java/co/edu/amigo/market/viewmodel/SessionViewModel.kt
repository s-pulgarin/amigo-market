package co.edu.amigo.market.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.model.User

// D1
class SessionViewModel : ViewModel() {
    var user by mutableStateOf<User?>(null)
        private set

    // Errores por campo; la pantalla los muestra bajo cada uno
    var emailError by mutableStateOf<String?>(null)
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set

    /**
     * Login simulado: acepta solo correos @amigo.edu.co y contraseñas de 6 o más caracteres.
     * Si es válido, arma el nombre a partir del correo y guarda el usuario.
     */
    fun login(email: String, password: String): Boolean {
        val cleanEmail = email.trim().lowercase()
        val localPart = cleanEmail.removeSuffix(DOMAIN)

        emailError = if (!cleanEmail.endsWith(DOMAIN) || localPart.isBlank() || '@' in localPart) {
            "Usa tu correo institucional $DOMAIN"
        } else null
        passwordError = if (password.length < MIN_PASSWORD) {
            "La contraseña debe tener al menos $MIN_PASSWORD caracteres"
        } else null

        if (emailError != null || passwordError != null) return false

        user = User(cleanEmail, nameFromEmail(localPart), "Desarrollo de Software")
        return true
    }

    /** Borra los errores, por ejemplo cuando el usuario vuelve a escribir. */
    fun clearErrors() {
        emailError = null
        passwordError = null
    }

    fun logout() {
        user = null
        clearErrors()
    }

    companion object {
        const val DOMAIN = "@amigo.edu.co"
        const val MIN_PASSWORD = 6

        /** "juan.salazar" -> "Juan Salazar" */
        fun nameFromEmail(localPart: String): String =
            localPart.split('.', '_', '-')
                .filter { it.isNotBlank() }
                .joinToString(" ") { part -> part.replaceFirstChar { it.uppercase() } }
    }
}
