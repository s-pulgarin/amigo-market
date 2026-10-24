package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.ui.components.ProductCard
import co.edu.amigo.market.ui.theme.AmigoMarketTheme
import co.edu.amigo.market.viewmodel.CartViewModel

@Composable
fun SearchScreen(cartVm: CartViewModel, onBusinessClick: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }

    // Se recalcula cada vez que cambia el texto
    val results = if (query.isBlank()) emptyList()
    else InMemoryRepository.search(query.trim())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Buscar productos") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Borrar búsqueda")
                    }
                }
            },
            singleLine = true
        )

        when {
            query.isBlank() -> MessageBox("Escribe el nombre de un producto para buscar")
            results.isEmpty() -> MessageBox("Sin resultados")
            else -> LazyColumn(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(results, key = { it.id }) { product ->
                    ProductCard(
                        product = product,
                        onAddToCart = { cartVm.add(product) },
                        modifier = Modifier.clickable { onBusinessClick(product.businessId) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBox(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScreenPreview() {
    AmigoMarketTheme {
        SearchScreen(cartVm = CartViewModel(), onBusinessClick = {})
    }
}