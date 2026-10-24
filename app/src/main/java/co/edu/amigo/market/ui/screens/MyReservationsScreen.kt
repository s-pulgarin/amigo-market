package co.edu.amigo.market.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Notes
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.model.Reservation
import co.edu.amigo.market.ui.theme.AmigoMarketTheme
import co.edu.amigo.market.viewmodel.ReservationViewModel

// Colores corporativos Universidad Católica Luis Amigó
private val AmigoBlue = Color(0xFF007B9A)
private val AmigoBlueDark = Color(0xFF01748F)
private val AmigoBlueLight = Color(0xFFE6F4F8)
private val AmigoOrange = Color(0xFFF29200)
private val AmigoOrangeLight = Color(0xFFFFF4E5)
private val AmigoSurface = Color(0xFFF8FAFC)
private val AmigoTextPrimary = Color(0xFF1E293B)
private val AmigoTextSecondary = Color(0xFF64748B)
private val AmigoError = Color(0xFFD32F2F)

/**
 * Pantalla "Mis reservas".
 *
 * [userName] es opcional para no romper la firma original: si se pasa
 * (p. ej. sessionVm.user?.name desde AppNavGraph), se muestra quién reservó.
 */
@Composable
fun MyReservationsScreen(
    vm: ReservationViewModel = viewModel(),
    userName: String? = null
) {
    // vm.reservations lee una mutableStateListOf del repositorio,
    // así que Compose se redibuja solo al agregar o cancelar.
    val reservations = vm.reservations

    // Reserva que el usuario quiere cancelar (null = diálogo cerrado)
    var toCancel by remember { mutableStateOf<Reservation?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AmigoSurface)
    ) {
        // Encabezado institucional Universidad Católica Luis Amigó
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(AmigoBlue)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color.White.copy(alpha = 0.18f),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.EventAvailable,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Mis reservas",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Turnos y espacios reservados en el campus",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmigoOrangeLight
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(50),
                    color = AmigoOrange
                ) {
                    Text(
                        text = "${reservations.size} ${if (reservations.size == 1) "activa" else "activas"}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        }

        // Franja corporativa naranja
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .background(AmigoOrange)
        )

        if (reservations.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = AmigoBlueLight,
                            modifier = Modifier.size(64.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.EventBusy,
                                    contentDescription = null,
                                    tint = AmigoBlue,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "No tienes reservas",
                            style = MaterialTheme.typography.titleMedium,
                            color = AmigoTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Aún no has reservado una mesa en el restaurante/cafetería ni un turno en fotocopiadora o papelería.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AmigoTextSecondary
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(reservations, key = { it.id }) { reservation ->
                    ReservationCard(
                        reservation = reservation,
                        userName = userName,
                        onCancelClick = { toCancel = reservation }
                    )
                }
            }
        }
    }

    toCancel?.let { reservation ->
        AlertDialog(
            onDismissRequest = { toCancel = null },
            title = {
                Text(
                    text = "Cancelar reserva",
                    fontWeight = FontWeight.Bold,
                    color = AmigoTextPrimary
                )
            },
            text = {
                Text(
                    text = "¿Seguro que quieres cancelar tu reserva en " +
                        "${reservation.businessName} el ${reservation.date} " +
                        "a las ${reservation.timeSlot}?",
                    color = AmigoTextSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.cancel(reservation.id)
                        InMemoryRepository.cancelReservation(reservation.id)
                        toCancel = null
                    }
                ) {
                    Text(
                        text = "Sí, cancelar",
                        color = AmigoError,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { toCancel = null }) {
                    Text(
                        text = "No",
                        color = AmigoBlueDark,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        )
    }
}

@Composable
private fun ReservationCard(
    reservation: Reservation,
    userName: String?,
    onCancelClick: () -> Unit
) {
    var expanded by rememberSaveable(reservation.id) { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(
            width = if (expanded) 1.5.dp else 1.dp,
            color = if (expanded) AmigoOrange else AmigoBlue.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = AmigoBlueLight,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Storefront,
                                contentDescription = null,
                                tint = AmigoBlue,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = reservation.businessName,
                            style = MaterialTheme.typography.titleMedium,
                            color = AmigoTextPrimary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Toca para ${if (expanded) "ocultar opciones" else "gestionar o cancelar"}",
                            style = MaterialTheme.typography.labelSmall,
                            color = AmigoTextSecondary
                        )
                    }
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AmigoBlue
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AmigoBlueLight.copy(alpha = 0.7f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = AmigoBlueDark,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Fecha: ${reservation.date}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmigoBlueDark,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = AmigoOrangeLight
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccessTime,
                            contentDescription = null,
                            tint = AmigoOrange,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Hora: ${reservation.timeSlot}",
                            style = MaterialTheme.typography.bodySmall,
                            color = AmigoBlueDark,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Group,
                        contentDescription = null,
                        tint = AmigoBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "Personas: ${reservation.people}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AmigoTextPrimary
                    )
                }

                if (!userName.isNullOrBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = AmigoBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            text = "Reservó: $userName",
                            style = MaterialTheme.typography.bodyMedium,
                            color = AmigoTextPrimary
                        )
                    }
                }
            }

            if (reservation.notes.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Notes,
                        contentDescription = null,
                        tint = AmigoTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text = "Notas: ${reservation.notes}",
                        style = MaterialTheme.typography.bodySmall,
                        color = AmigoTextSecondary
                    )
                }
            }

            // Al seleccionar la tarjeta aparece el botón Cancelar
            AnimatedVisibility(visible = expanded) {
                Column {
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onCancelClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AmigoError,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Cancelar reserva",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MyReservationsScreenPreview() {
    if (InMemoryRepository.getReservations().isEmpty()) {
        InMemoryRepository.addReservation(
            Reservation(
                id = "demo-1",
                businessId = "copias",
                businessName = "Fotocopiadora Amigó",
                date = "Hoy",
                timeSlot = "10:00",
                people = 1,
                notes = "Impresión a color y anillado de tesis"
            )
        )
        InMemoryRepository.addReservation(
            Reservation(
                id = "demo-2",
                businessId = "resto",
                businessName = "Restaurante Universitario",
                date = "Mañana",
                timeSlot = "14:00",
                people = 4,
                notes = "Mesa para almuerzo de grupo de estudio"
            )
        )
    }
    AmigoMarketTheme {
        MyReservationsScreen(userName = "Miguel Ángel Pulido")
    }
}
