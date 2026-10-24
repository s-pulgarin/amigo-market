package co.edu.amigo.market.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.ui.theme.AmigoMarketTheme

// Colores corporativos Universidad Católica Luis Amigó
private val AmigoPrimaryBlue = Color(0xFF007B9A)
private val AmigoBlueDark = Color(0xFF01748F)
private val AmigoBlueLight = Color(0xFFE6F4F8)
private val AmigoSecondaryOrange = Color(0xFFF29200)
private val AmigoOrangeLight = Color(0xFFFFF4E5)

// C2 · Selector de horario para turnos o espacios universitarios
@Composable
fun TimeSlotPicker(
    slots: List<String>,
    selected: String?,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = ((slots.size + 2) / 3).coerceAtLeast(1)
    val gridMaxHeight = (rows * 54 + 12).dp

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = gridMaxHeight),
        userScrollEnabled = false,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(slots) { slot ->
            val isSelected = slot == selected
            FilterChip(
                selected = isSelected,
                onClick = { onSelect(slot) },
                label = {
                    Text(
                        text = slot,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = if (isSelected) Icons.Default.CheckCircle else Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                colors = FilterChipDefaults.filterChipColors(
                    // El horario elegido se resalta con el color primario institucional
                    selectedContainerColor = AmigoPrimaryBlue,
                    selectedLabelColor = Color.White,
                    selectedLeadingIconColor = AmigoSecondaryOrange,
                    containerColor = AmigoBlueLight.copy(alpha = 0.7f),
                    labelColor = AmigoBlueDark,
                    iconColor = AmigoPrimaryBlue
                ),
                border = FilterChipDefaults.filterChipBorder(
                    enabled = true,
                    selected = isSelected,
                    borderColor = AmigoPrimaryBlue.copy(alpha = 0.3f),
                    selectedBorderColor = AmigoSecondaryOrange,
                    selectedBorderWidth = 1.5.dp
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true, name = "C2 · TimeSlotPicker Universitario")
@Composable
private fun TimeSlotPickerPreview() {
    val sampleSlots = listOf("8:00", "9:00", "10:00", "11:00", "14:00", "15:00", "16:00")
    var selectedSlot by remember { mutableStateOf<String?>("10:00") }

    AmigoMarketTheme {
        Surface(color = Color(0xFFF8FAFC)) {
            Column(modifier = Modifier.padding(16.dp)) {
                // Encabezado de contexto universitario
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(AmigoPrimaryBlue, RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Storefront,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Reserva de turno o espacio en el campus",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Cafetería · Restaurante · Fotocopiadora · Papelería · Librería · Tienda Amigó",
                                style = MaterialTheme.typography.bodySmall,
                                color = AmigoOrangeLight
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .background(AmigoSecondaryOrange)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, AmigoPrimaryBlue.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Selecciona la hora de tu turno o mesa",
                            style = MaterialTheme.typography.titleMedium,
                            color = AmigoBlueDark,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        TimeSlotPicker(
                            slots = sampleSlots,
                            selected = selectedSlot,
                            onSelect = { selectedSlot = it }
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = AmigoOrangeLight,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Turno seleccionado: ${selectedSlot ?: "Ninguno"}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = AmigoBlueDark,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
