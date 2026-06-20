package at.fh.kis.g1.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.fh.kis.g1.weatherapp.data.model.WeatherResponse
import at.fh.kis.g1.weatherapp.ui.WeatherViewModel

@Composable
fun TopWeatherSection(
    data: WeatherResponse,
    viewModel: WeatherViewModel,
    isCelsius: Boolean
) {
    val current = data.list.first()
    val city = data.city
    val condition = current.weather.firstOrNull()

    Card(modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.large) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = viewModel.formatCurrentDateTime(city.timezone),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = viewModel.formatTemp(current.main.temp, isCelsius),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = city.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = condition?.description?.replaceFirstChar { it.uppercase() } ?: "",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "H: ${viewModel.formatTemp(current.main.tempMax, isCelsius)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        "L: ${viewModel.formatTemp(current.main.tempMin, isCelsius)}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Icon(
                imageVector = weatherIconVector(condition?.main ?: ""),
                contentDescription = condition?.main,
                modifier = Modifier.size(72.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
