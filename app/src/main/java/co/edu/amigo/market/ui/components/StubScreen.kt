package co.edu.amigo.market.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/** Pantalla provisional mientras se implementa la real. Borrar cuando no se use. */
@Composable
fun StubScreen(
    title: String,
    vararg actions: Pair<String, () -> Unit>
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title, style = MaterialTheme.typography.headlineMedium)
            Text("Pantalla en construcción", style = MaterialTheme.typography.bodyMedium)
            actions.forEach { (label, onClick) ->
                Button(onClick = onClick) { Text(label) }
            }
        }
    }
}
