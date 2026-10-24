package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.amigo.market.data.model.CartItem
import co.edu.amigo.market.data.model.Product
import co.edu.amigo.market.viewmodel.CartViewModel
import java.text.NumberFormat
import java.util.Locale

// Pesos colombianos: 2500 -> "$ 2.500"
private fun formatCop(value: Int): String =
    "$ " + NumberFormat.getNumberInstance(Locale.forLanguageTag("es-CO")).format(value)

@Composable
fun CartScreen(cartVm: CartViewModel, onCheckout: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            if (cartVm.items.isEmpty()) {
                EmptyCart()
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(cartVm.items.toList(), key = { it.product.id }) { item ->
                        CartItemRow(
                            item = item,
                            onDecrease = { cartVm.decrease(item.product.id) },
                            onIncrease = { cartVm.add(item.product) },
                            onRemove = { cartVm.remove(item.product.id) }
                        )
                    }
                }
            }
        }
        // Total y botón de pago
        Surface(tonalElevation = 3.dp, modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total", style = MaterialTheme.typography.titleMedium)
                    Text(formatCop(cartVm.total), style = MaterialTheme.typography.titleLarge)
                }
                Button(
                    onClick = onCheckout,
                    enabled = cartVm.items.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Ir a pagar") }
            }
        }
    }
}

@Composable
private fun EmptyCart() {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            Icons.Default.ShoppingCart, contentDescription = null,
            modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outline
        )
        Spacer(Modifier.height(8.dp))
        Text("Tu carrito está vacío", style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onDecrease: () -> Unit,
    onIncrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(
                    item.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis // Nombre largo en una línea
                )
                Text(
                    formatCop(item.product.price * item.quantity),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = onDecrease) { Text("−", style = MaterialTheme.typography.titleLarge) }
            Text("${item.quantity}", style = MaterialTheme.typography.titleMedium)
            IconButton(onClick = onIncrease) { Icon(Icons.Default.Add, "Aumentar") }
            IconButton(onClick = onRemove) { Icon(Icons.Default.Delete, "Eliminar") }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CartScreenPreview() {
    val vm = CartViewModel().apply {
        items.add(CartItem(Product("p1", "cafe", "Tinto grande", 2500, "Café 12 oz"), 2))
        items.add(CartItem(Product("p2", "cafe", "Empanada", 3000, "De carne"), 1))
    }
    CartScreen(vm) {}
}

@Preview(showBackground = true)
@Composable
private fun CartScreenEmptyPreview() {
    CartScreen(CartViewModel()) {}
}