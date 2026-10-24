package co.edu.amigo.market

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.edu.amigo.market.ui.navigation.AppNavGraph
import co.edu.amigo.market.ui.theme.AmigoMarketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AmigoMarketTheme { AppNavGraph() } }
    }
}
