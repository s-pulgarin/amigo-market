package co.edu.amigo.market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import co.edu.amigo.market.ui.navigation.AppNavGraph
import co.edu.amigo.market.ui.theme.Amigo_marketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Amigo_marketTheme {
                AppNavGraph()
            }
        }
    }
}
