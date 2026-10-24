package co.edu.amigo.market.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LightColors = lightColorScheme(
    primary = AmigoBlue,
    onPrimary = Color.White,
    primaryContainer = AmigoBlueContainer,
    onPrimaryContainer = AmigoBlueDark,
    secondary = AmigoYellow,
    onSecondary = Color.Black,
    secondaryContainer = AmigoYellowContainer,
    onSecondaryContainer = AmigoYellowDark,
    background = AmigoBackgroundLight,
    onBackground = AmigoTextLight,
    surface = AmigoSurfaceLight,
    onSurface = AmigoTextLight,
    error = AmigoError
)

private val DarkColors = darkColorScheme(
    primary = AmigoBlueLight,
    onPrimary = AmigoBlueDark,
    primaryContainer = AmigoBlue,
    onPrimaryContainer = AmigoBlueContainer,
    secondary = AmigoYellow,
    onSecondary = Color.Black,
    secondaryContainer = AmigoYellowDark,
    onSecondaryContainer = AmigoYellowContainer,
    background = AmigoBackgroundDark,
    onBackground = AmigoTextDark,
    surface = AmigoSurfaceDark,
    onSurface = AmigoTextDark,
    error = AmigoErrorDark
)

private val AmigoTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 30.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp,
        lineHeight = 22.sp
    )
)

@Composable
fun AmigoMarketTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors
    MaterialTheme(
        colorScheme = colors,
        typography = AmigoTypography,
        content = content
    )
}

@Preview(name = "Claro", showBackground = true)
@Preview(name = "Oscuro", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AmigoMarketThemePreview() {
    AmigoMarketTheme {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Amigó Market", style = MaterialTheme.typography.titleLarge)
                Text("Cafetería Central", style = MaterialTheme.typography.titleMedium)
                Text(
                    "Café, desayunos y snacks",
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(onClick = {}, modifier = Modifier.padding(top = 12.dp)) {
                    Text("Agregar")
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                ) {
                    Text(
                        "Esto es una tarjeta",
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}