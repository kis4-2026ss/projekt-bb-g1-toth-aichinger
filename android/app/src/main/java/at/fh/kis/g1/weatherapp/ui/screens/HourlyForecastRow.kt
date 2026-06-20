package at.fh.kis.g1.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import at.fh.kis.g1.weatherapp.data.model.ListItem
import at.fh.kis.g1.weatherapp.ui.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun HourlyForecastItem(
    item: ListItem,
    timezoneOffsetSeconds: Long,
    viewModel: WeatherViewModel,
    isCelsius: Boolean
) {
    val condition = item.weather.firstOrNull()

    Card(shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = viewModel.formatHourlyTime(item.dt, timezoneOffsetSeconds),
                style = MaterialTheme.typography.labelSmall
            )
            Icon(
                imageVector = weatherIconVector(condition?.main ?: ""),
                contentDescription = condition?.main,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = viewModel.formatTemp(item.main.temp, isCelsius),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.WaterDrop,
                    contentDescription = null,
                    modifier = Modifier.size(10.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${item.main.humidity.roundToInt()}%",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}
