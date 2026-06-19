package at.fh.kis.g1.weatherapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import at.fh.kis.g1.weatherapp.ui.WeatherUiState
import at.fh.kis.g1.weatherapp.ui.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel,
    onRequestLocationPermission: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val isCelsius by viewModel.isCelsius.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    var showSearchDialog by remember { mutableStateOf(false) }
    var showSettingsSheet by remember { mutableStateOf(false) }

    when (val state = uiState) {
        is WeatherUiState.Empty -> WelcomeScreen(
            onRequestLocation = onRequestLocationPermission,
            onSearchCity = { showSearchDialog = true }
        )

        is WeatherUiState.Loading -> LoadingScreen()

        is WeatherUiState.Error -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = { showSearchDialog = true }) { Text("Search a City") }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onRequestLocationPermission) { Text("Use My Location") }
            }
        }

        is WeatherUiState.Success -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(state.data.city.name) },
                        actions = {
                            IconButton(onClick = { showSearchDialog = true }) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                            IconButton(onClick = { showSettingsSheet = true }) {
                                Icon(Icons.Default.Settings, contentDescription = "Settings")
                            }
                        }
                    )
                }
            ) { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        TopWeatherSection(state.data, viewModel, isCelsius)
                    }
                    item {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(state.data.list) { hourly ->
                                HourlyForecastItem(
                                    item = hourly,
                                    timezoneOffsetSeconds = state.data.city.timezone,
                                    viewModel = viewModel,
                                    isCelsius = isCelsius
                                )
                            }
                        }
                    }
                    item {
                        SunriseSunsetCard(
                            sunriseTime = viewModel.formatSunTime(
                                state.data.city.sunrise,
                                state.data.city.timezone
                            ),
                            sunsetTime = viewModel.formatSunTime(
                                state.data.city.sunset,
                                state.data.city.timezone
                            )
                        )
                    }
                    item {
                        IndicatorsGrid(
                            current = state.data.list.first(),
                            viewModel = viewModel,
                            isCelsius = isCelsius
                        )
                    }
                    item {
                        val dailyForecasts = viewModel.getDailyForecasts(state.data)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.large
                        ) {
                            Column {
                                dailyForecasts.forEachIndexed { index, daily ->
                                    DailyForecastRow(daily, viewModel, isCelsius)
                                    if (index < dailyForecasts.lastIndex) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(horizontal = 16.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSearchDialog) {
        AddLocationDialog(
            query = searchQuery,
            onQueryChange = viewModel::updateSearchQuery,
            onSearch = { city ->
                viewModel.loadWeather(city)
                showSearchDialog = false
            },
            onDismiss = { showSearchDialog = false }
        )
    }

    if (showSettingsSheet) {
        ManageLocationsSheet(
            isCelsius = isCelsius,
            onToggleUnit = viewModel::toggleUnit,
            onDismiss = { showSettingsSheet = false }
        )
    }
}
