package co.edu.amigo.market.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import co.edu.amigo.market.viewmodel.CartViewModel

@Composable
fun BusinessDetailScreen(
    businessId: String, cartVm: CartViewModel,
    onReserve: () -> Unit, onGoToCart: () -> Unit, onBack: () -> Unit
) {
    Text("TODO BusinessDetailScreen")
}
