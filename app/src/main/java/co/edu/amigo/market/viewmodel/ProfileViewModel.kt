package co.edu.amigo.market.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.Repository

/** Perfil, pedidos y publicaciones del usuario. TODO: exponer UiState y acciones. */
class ProfileViewModel(
    private val repository: Repository = Repository
) : ViewModel()
