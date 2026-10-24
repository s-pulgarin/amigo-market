package co.edu.amigo.market.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/** Selector de horarios en grilla de 3 columnas; el horario elegido se resalta con el color primario. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSlotPicker(slots: List<String>, selected: String?, onSelect: (String) -> Unit, modifier: Modifier = Modifier) {
    // Altura fija según la cantidad de filas, para poder usarla dentro de un formulario con scroll
    val filas = (slots.size + 2) / 3
    val alto = (filas * 48).dp + ((filas - 1).coerceAtLeast(0) * 8).dp + 16.dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth().height(alto),
        userScrollEnabled = false,
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(slots) { slot ->
            Box(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                contentAlignment = Alignment.Center
            ) {
                FilterChip(
                    selected = slot == selected,
                    onClick = { onSelect(slot) },
                    label = {
                        Text(
                            text = slot,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimeSlotPickerPreview() {
    val slots = listOf(
        "08:00", "09:00", "10:00",
        "11:00", "12:00", "14:00",
        "15:00", "16:00", "17:00"
    )
    var selected by remember { mutableStateOf<String?>("10:00") }

    MaterialTheme {
        TimeSlotPicker(
            slots = slots,
            selected = selected,
            onSelect = { selected = it }
        )
    }
}