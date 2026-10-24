package co.edu.amigo.market.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.Repository

/** Sesión del usuario (login/logout). TODO: exponer UiState y acciones. */
class AuthViewModel(
    private val repository: Repository = Repository
) : ViewModel()
