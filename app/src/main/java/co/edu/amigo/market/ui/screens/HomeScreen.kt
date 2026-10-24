package co.edu.amigo.market.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import co.edu.amigo.market.data.InMemoryRepository
import co.edu.amigo.market.ui.components.BusinessCard

// Paleta de la pantalla de inicio: azul, blanco y naranja
private val AzulAmigo = Color(0xFF0D47A1)
private val AzulClaro = Color(0xFF1976D2)
private val NaranjaAmigo = Color(0xFFFF8F00)
private val Blanco = Color(0xFFFFFFFF)
private val GrisFondo = Color(0xFFF1F5FB)

@Composable
fun HomeScreen(
    onBusinessClick: (String) -> Unit,
    onSeeAll: () -> Unit,
    onSearch: () -> Unit
) {
    val topBusinesses = remember {
        InMemoryRepository.getBusinesses()
            .sortedByDescending { it.rating }
            .take(4)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Blanco)
            .verticalScroll(rememberScrollState())
    ) {
        // Encabezado azul con saludo y buscador
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(listOf(AzulAmigo, AzulClaro)),
                    RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp)
                )
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column {
                Text(
                    text = "¡Hola, bienvenido a Amigó Market!",
                    color = Blanco,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "¿Qué se te antoja hoy?",
                    color = Blanco.copy(alpha = 0.85f),
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(20.dp))

                // Barra de búsqueda falsa: solo navega al tocarla
                Surface(
                    onClick = onSearch,
                    shape = RoundedCornerShape(50),
                    color = Blanco,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = NaranjaAmigo
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Buscar productos...",
                            color = Color(0xFF6B7280),
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Box(
                Modifier
                    .width(5.dp)
                    .height(22.dp)
                    .background(NaranjaAmigo, RoundedCornerShape(3.dp))
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "Mejor calificados",
                color = AzulAmigo,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(Modifier.height(12.dp))

        if (topBusinesses.isEmpty()) {
            Text(
                text = "No hay negocios disponibles",
                color = Color(0xFF6B7280),
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(topBusinesses, key = { it.id }) { business ->
                    BusinessCard(
                        business = business,
                        onClick = { onBusinessClick(business.id) },
                        modifier = Modifier.width(280.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(28.dp))

        Button(
            onClick = onSeeAll,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = NaranjaAmigo,
                contentColor = Blanco
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .height(52.dp)
        ) {
            Text("Ver todos los negocios", fontWeight = FontWeight.Bold)
        }

        Spacer(Modifier.height(24.dp))
    }
}
