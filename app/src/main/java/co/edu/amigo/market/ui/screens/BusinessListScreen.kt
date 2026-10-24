package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.model.Business
import co.edu.amigo.market.data.model.BusinessCategory // AJUSTAR: si category es String, quita este import
import co.edu.amigo.market.ui.components.BusinessCard
import co.edu.amigo.market.ui.components.CategoryChips

@Composable
fun BusinessListScreen(
    businesses: List<Business>,
    onBusinessClick: (String) -> Unit, // AJUSTAR: si el id es Int, cambia a (Int) -> Unit
    modifier: Modifier = Modifier
) {
    // La categoría seleccionada sobrevive a rotaciones y a volver desde otra pantalla.
    // null = "Todas"
    var selectedCategory by rememberSaveable { mutableStateOf<BusinessCategory?>(null) }

    val filteredBusinesses = if (selectedCategory == null) {
        businesses
    } else {
        businesses.filter { it.category == selectedCategory }
    }

    Column(modifier = modifier.fillMaxSize()) {

        CategoryChips(
            selected = selectedCategory,                         // AJUSTAR nombres de parámetros
            onCategorySelected = { selectedCategory = it }       // AJUSTAR
        )

        if (filteredBusinesses.isEmpty()) {
            Text(
                text = "No hay negocios en esta categoría",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredBusinesses, key = { it.id }) { business ->
                    BusinessCard(
                        business = business,                                // AJUSTAR
                        onClick = { onBusinessClick(business.id) }          // AJUSTAR
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BusinessListScreenPreview() {
    // AJUSTAR: usa la lista de ejemplo que tenga SeedData
    // BusinessListScreen(businesses = SeedData.businesses, onBusinessClick = {})
}
