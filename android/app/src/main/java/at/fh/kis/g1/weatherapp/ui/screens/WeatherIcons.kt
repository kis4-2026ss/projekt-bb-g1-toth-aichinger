package at.fh.kis.g1.weatherapp.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Grain
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Thunderstorm
import androidx.compose.material.icons.filled.Umbrella
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector

fun weatherIconVector(condition: String): ImageVector = when (condition) {
    "Clear" -> Icons.Default.WbSunny
    "Clouds" -> Icons.Default.Cloud
    "Rain" -> Icons.Default.Umbrella
    "Snow" -> Icons.Default.AcUnit
    "Thunderstorm" -> Icons.Default.Thunderstorm
    "Drizzle" -> Icons.Default.Grain
    "Mist", "Fog", "Haze", "Smoke", "Dust", "Sand", "Ash" -> Icons.Default.WbCloudy
    else -> Icons.Default.Help
}
