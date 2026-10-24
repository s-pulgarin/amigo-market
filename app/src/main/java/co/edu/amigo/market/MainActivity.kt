package co.edu.amigo.market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import co.edu.amigo.market.ui.screens.MyReservationsScreen
import co.edu.amigo.market.ui.screens.ReservationScreen
import co.edu.amigo.market.ui.theme.AmigoMarketTheme
import co.edu.amigo.market.viewmodel.ReservationViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AmigoMarketTheme {
                val reservationVm: ReservationViewModel = viewModel()
                var selectedTab by remember { mutableIntStateOf(0) }

                Column(modifier = Modifier.fillMaxSize()) {
                    SecondaryTabRow(
                        selectedTabIndex = selectedTab,
                        containerColor = Color(0xFF01748F),
                        contentColor = Color.White,
                        indicator = {
                            TabRowDefaults.SecondaryIndicator(
                                Modifier.tabIndicatorOffset(selectedTab),
                                color = Color(0xFFF29200)
                            )
                        }
                    ) {
                        Tab(
                            selected = selectedTab == 0,
                            onClick = { selectedTab = 0 },
                            text = {
                                Text(
                                    text = "Nueva Reserva (C3)",
                                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        )
                        Tab(
                            selected = selectedTab == 1,
                            onClick = { selectedTab = 1 },
                            text = {
                                Text(
                                    text = "Mis Reservas (${reservationVm.reservations.size})",
                                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
                                    color = Color.White
                                )
                            }
                        )
                    }

                    if (selectedTab == 0) {
                        ReservationScreen(
                            businessId = "cafe",
                            onDone = { selectedTab = 1 },
                            vm = reservationVm
                        )
                    } else {
                        MyReservationsScreen(
                            vm = reservationVm
                        )
                    }
                }
            }
        }
    }
}
