package at.fh.kis.g1.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.DeviceThermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.fh.kis.g1.weatherapp.data.model.ListItem
import at.fh.kis.g1.weatherapp.ui.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun IndicatorsGrid(
    current: ListItem,
    viewModel: WeatherViewModel,
    isCelsius: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IndicatorCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.DeviceThermostat,
            label = "Feels Like",
            value = viewModel.formatTemp(current.main.feelsLike, isCelsius)
        )
        IndicatorCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.WaterDrop,
            label = "Humidity",
            value = "${current.main.humidity.roundToInt()}%"
        )
        IndicatorCard(
            modifier = Modifier.weight(1f),
            icon = Icons.Default.Air,
            label = "Wind",
            value = "${current.wind.speed.roundToInt()} m/s"
        )
    }
}

@Composable
private fun IndicatorCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    value: String
) {
    Card(modifier = modifier, shape = MaterialTheme.shapes.large) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.padding(0.dp))
                Text(label, style = MaterialTheme.typography.labelSmall)
            }
            Text(value, fontSize = 20.sp)
        }
    }
}
