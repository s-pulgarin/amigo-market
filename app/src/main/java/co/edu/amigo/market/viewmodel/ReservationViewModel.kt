package co.edu.amigo.market.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.MarketRepository
import co.edu.amigo.market.data.model.Reservation
import java.util.UUID

// C1
class ReservationViewModel(private val repo: MarketRepository = InMemoryRepository) : ViewModel() {
    val availableSlots = listOf("8:00", "9:00", "10:00", "11:00", "14:00", "15:00", "16:00")
    var selectedSlot by mutableStateOf<String?>(null)
    var people by mutableStateOf(1)
    var notes by mutableStateOf("")
    var error by mutableStateOf<String?>(null)
    val reservations: List<Reservation> get() = repo.getReservations()

    fun confirm(businessId: String, date: String): Boolean {
        val slot = selectedSlot
        if (slot.isNullOrBlank()) {
            error = "Selecciona una franja horaria para tu turno o espacio"
            return false
        }
        if (people < 1) {
            error = "El número de personas debe ser al menos 1"
            return false
        }
        if (date.isBlank()) {
            error = "Selecciona una fecha válida (Hoy o Mañana)"
            return false
        }

        val businessName = repo.getBusiness(businessId)?.name ?: when (businessId) {
            "cafe" -> "Cafetería Central"
            "restaurante", "resto" -> "Restaurante Universitario"
            "copias" -> "Fotocopiadora Amigó"
            "papeleria", "papel" -> "Papelería Universitaria"
            "libreria", "libros" -> "Librería Editorial Amigó"
            "tienda" -> "Tienda Institucional Amigó"
            else -> businessId.ifBlank { "Negocio Universitario" }
        }

        val reservation = Reservation(
            id = UUID.randomUUID().toString(),
            businessId = businessId,
            businessName = businessName,
            date = date,
            timeSlot = slot,
            people = people,
            notes = notes.trim()
        )
        repo.addReservation(reservation)

        // Limpiar estado tras confirmar
        selectedSlot = null
        people = 1
        notes = ""
        error = null
        return true
    }

    fun cancel(reservationId: String) {
        repo.cancelReservation(reservationId)
    }
}
