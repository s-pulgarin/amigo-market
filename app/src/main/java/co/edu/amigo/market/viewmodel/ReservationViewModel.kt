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

    /**
     * Valida que se haya seleccionado un horario y que el número de personas esté entre 1 y 10.
     * Si falla, llena [error] y devuelve false.
     * Si es válida, crea la [Reservation] con el nombre del negocio, la guarda y limpia el formulario.
     */
    fun confirm(businessId: String, date: String): Boolean {
        val slot = selectedSlot
        if (slot.isNullOrBlank()) {
            error = "Selecciona un horario disponible"
            return false
        }
        if (people !in 1..10) {
            error = "El número de personas debe estar entre 1 y 10"
            return false
        }

        val businessName = repo.getBusiness(businessId)?.name ?: businessId
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

        selectedSlot = null
        people = 1
        notes = ""
        error = null
        return true
    }

    /** Elimina la reserva del repositorio. */
    fun cancel(reservationId: String) {
        repo.cancelReservation(reservationId)
    }
}
