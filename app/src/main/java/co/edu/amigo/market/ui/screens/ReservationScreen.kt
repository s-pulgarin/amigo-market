package co.edu.amigo.market.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.amigo.market.viewmodel.ReservationViewModel

@Composable
fun ReservationScreen(
    businessId: String, onDone: () -> Unit,
    vm: ReservationViewModel = viewModel()
) {
    Text("TODO ReservationScreen")
}
