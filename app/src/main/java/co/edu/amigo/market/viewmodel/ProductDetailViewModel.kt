package co.edu.amigo.market.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.Repository

/** Detalle de un producto y agregar al carrito. TODO: exponer UiState y acciones. */
class ProductDetailViewModel(
    private val repository: Repository = Repository
) : ViewModel()
