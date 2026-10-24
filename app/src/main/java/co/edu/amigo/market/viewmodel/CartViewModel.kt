package co.edu.amigo.market.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.Repository

/** Carrito y checkout. TODO: exponer UiState y acciones. */
class CartViewModel(
    private val repository: Repository = Repository
) : ViewModel()
