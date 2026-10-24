package co.edu.amigo.market.data

import androidx.compose.runtime.mutableStateListOf
import co.edu.amigo.market.data.model.Business
import co.edu.amigo.market.data.model.Order
import co.edu.amigo.market.data.model.Product
import co.edu.amigo.market.data.model.Reservation

interface MarketRepository {
    fun getBusinesses(): List<Business>
    fun getBusiness(id: String): Business?
    fun getProducts(businessId: String): List<Product>
    fun search(query: String): List<Product>
    fun placeOrder(order: Order)
    fun getOrders(): List<Order>
    fun addReservation(reservation: Reservation)
    fun cancelReservation(id: String)
    fun getReservations(): List<Reservation>
}

object InMemoryRepository : MarketRepository {
    // Listas observables: Compose se redibuja cuando cambian
    private val orders = mutableStateListOf<Order>()
    private val reservations = mutableStateListOf<Reservation>()

    override fun getBusinesses() = SeedData.businesses
    override fun getBusiness(id: String) = SeedData.businesses.find { it.id == id }
    override fun getProducts(businessId: String) =
        SeedData.products.filter { it.businessId == businessId }
    override fun search(query: String) =
        SeedData.products.filter { it.name.contains(query, ignoreCase = true) }
    override fun placeOrder(order: Order) { orders.add(0, order) }
    override fun getOrders(): List<Order> = orders
    override fun addReservation(reservation: Reservation) { reservations.add(0, reservation) }
    override fun cancelReservation(id: String) { reservations.removeAll { it.id == id } }
    override fun getReservations(): List<Reservation> = reservations
}
