package co.edu.amigo.market.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.data.MarketRepository
import co.edu.amigo.market.data.model.Reservation

// C1
class ReservationViewModel(private val repo: MarketRepository = InMemoryRepository) : ViewModel() {
    val availableSlots = listOf("8:00", "9:00", "10:00", "11:00", "14:00", "15:00", "16:00")
    var selectedSlot by mutableStateOf<String?>(null)
    var people by mutableStateOf(1)
    var notes by mutableStateOf("")
    var error by mutableStateOf<String?>(null)
    val reservations: List<Reservation> get() = repo.getReservations()
    fun confirm(businessId: String, date: String): Boolean { /* TODO(C1) */ return false }
    fun cancel(reservationId: String) { /* TODO(C1) */ }
}
