
package co.edu.amigo.market.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.model.Category

@Composable
fun CategoryChips(
    selected: Category?,
    onSelect: (Category?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item(key = "todos") {
            FilterChip(
                selected = selected == null,
                onClick = { onSelect(null) },
                label = { Text("Todos") }
            )
        }

        items(
            items = Category.values().toList(),
            key = { category -> category.name }
        ) { category ->
            FilterChip(
                selected = selected == category,
                onClick = { onSelect(category) },
                label = { Text(category.label) }
            )
        }
    }
}

@Preview(showBackground = true, name = "Filtro por categorías")
@Composable
private fun CategoryChipsPreview() {
    var selected by remember { mutableStateOf<Category?>(null) }

    MaterialTheme {
        CategoryChips(
            selected = selected,
            onSelect = { selected = it }
        )
    }
}
