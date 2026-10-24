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
    fun login(email: String, password: String): Boolean {
        user = User(email, "Estudiante", "Desarrollo de Software") // TODO(D1): validar
        return true
    }
    fun logout() { user = null }
}
