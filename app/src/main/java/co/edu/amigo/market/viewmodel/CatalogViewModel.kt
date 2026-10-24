package co.edu.amigo.market.viewmodel

import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.Repository

/** Catálogo: listado, búsqueda y filtro por categoría. TODO: exponer UiState y acciones. */
class CatalogViewModel(
    private val repository: Repository = Repository
) : ViewModel()
