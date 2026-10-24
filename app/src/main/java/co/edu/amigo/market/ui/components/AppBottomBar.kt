package com.tuapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Representa cada destino de la barra de navegación inferior.
 * El "route" debe coincidir con la ruta usada en el NavHost del proyecto.
 */
enum class BottomBarDestination(val route: String, val label: String) {
    Inicio(route = "inicio", label = "Inicio"),
    Negocios(route = "negocios", label = "Negocios"),
    Carrito(route = "carrito", label = "Carrito"),
    Perfil(route = "perfil", label = "Perfil")
}

/**
 * Barra de navegación inferior de la app.
 *
 * @param currentRoute ruta actualmente seleccionada (viene del NavController).
 * @param cartCount cantidad de productos en el carrito; si es mayor que 0,
 *                   se muestra un Badge sobre el ícono de Carrito.
 * @param onNavigate callback invocado con la ruta del ítem presionado.
 */
@Composable
fun AppBottomBar(
    currentRoute: String?,
    cartCount: Int,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        BottomBarDestination.entries.forEach { destination ->
            val isSelected = currentRoute == destination.route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(destination.route) },
                label = { Text(destination.label) },
                icon = {
                    when (destination) {
                        BottomBarDestination.Inicio -> Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = destination.label
                        )

                        BottomBarDestination.Negocios -> Icon(
                            imageVector = Icons.Filled.Store,
                            contentDescription = destination.label
                        )

                        BottomBarDestination.Carrito -> {
                            if (cartCount > 0) {
                                BadgedBox(
                                    badge = {
                                        Badge {
                                            Text(
                                                text = if (cartCount > 99) "99+" else cartCount.toString()
                                            )
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.ShoppingCart,
                                        contentDescription = destination.label
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.ShoppingCart,
                                    contentDescription = destination.label
                                )
                            }
                        }

                        BottomBarDestination.Perfil -> Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = destination.label
                        )
                    }
                }
            )
        }
    }
}