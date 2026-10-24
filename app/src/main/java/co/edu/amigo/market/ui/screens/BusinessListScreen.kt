package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.model.Category
import co.edu.amigo.market.ui.components.BusinessCard
import co.edu.amigo.market.ui.components.CategoryChips

@Composable
fun BusinessListScreen(
    onBusinessClick: (String) -> Unit
) {
    // Los negocios salen del repositorio en memoria
    val businesses = remember { InMemoryRepository.getBusinesses() }

    // null = "Todas". rememberSaveable conserva el filtro al rotar o volver a la pantalla.
    var selectedCategory by rememberSaveable { mutableStateOf<Category?>(null) }

    val filteredBusinesses = if (selectedCategory == null) {
        businesses
    } else {
        businesses.filter { it.category == selectedCategory }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        CategoryChips(
            selected = selectedCategory,
            onSelect = { selectedCategory = it }
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
                        business = business,
                        onClick = { onBusinessClick(business.id) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BusinessListScreenPreview() {
    BusinessListScreen(onBusinessClick = {})
}