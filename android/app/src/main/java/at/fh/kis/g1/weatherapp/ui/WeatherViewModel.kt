package at.fh.kis.g1.weatherapp.ui

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import at.fh.kis.g1.weatherapp.data.model.WeatherResponse
import at.fh.kis.g1.weatherapp.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("weathery_prefs")

sealed class WeatherUiState {
    object Empty : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(val data: WeatherResponse) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeatherRepository(application)
    private val isCelsiusKey = booleanPreferencesKey("is_celsius")

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _isCelsius = MutableStateFlow(true)
    val isCelsius: StateFlow<Boolean> = _isCelsius.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            application.dataStore.data
                .map { it[isCelsiusKey] ?: true }
                .collect { _isCelsius.value = it }
        }
    }

    fun loadWeather(cityName: String) {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            _uiState.value = repository.getWeatherByCity(cityName).fold(
                onSuccess = { WeatherUiState.Success(it) },
                onFailure = { WeatherUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun loadWeatherForLocation(lat: Double, lon: Double) {
        _uiState.value = WeatherUiState.Loading
        viewModelScope.launch {
            _uiState.value = repository.getWeatherByLocation(lat, lon).fold(
                onSuccess = { WeatherUiState.Success(it) },
                onFailure = { WeatherUiState.Error(it.message ?: "Unknown error") }
            )
        }
    }

    fun toggleUnit() {
        viewModelScope.launch {
            val next = !_isCelsius.value
            _isCelsius.value = next
            getApplication<Application>().dataStore.edit { it[isCelsiusKey] = next }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun convertTemp(celsius: Double): Double =
        if (_isCelsius.value) celsius else celsius * 9.0 / 5.0 + 32.0

    fun formatTemp(celsius: Double): String =
        "${convertTemp(celsius).roundToInt()}°"

    fun formatHourlyTime(unixSeconds: Long, timezoneOffsetSeconds: Long): String =
        DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds.toInt()))
            .format(Instant.ofEpochSecond(unixSeconds))

    fun formatCurrentDateTime(timezoneOffsetSeconds: Long): String =
        DateTimeFormatter.ofPattern("E, HH:mm")
            .withZone(ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds.toInt()))
            .format(Instant.now())

    fun formatSunTime(unixSeconds: Long, timezoneOffsetSeconds: Long): String =
        DateTimeFormatter.ofPattern("HH:mm")
            .withZone(ZoneOffset.ofTotalSeconds(timezoneOffsetSeconds.toInt()))
            .format(Instant.ofEpochSecond(unixSeconds))

    data class DailyForecast(
        val dateKey: String,
        val dayLabel: String,
        val maxTemp: Double,
        val minTemp: Double,
        val main: String
    )

    fun getDailyForecasts(data: WeatherResponse): List<DailyForecast> =
        data.list
            .groupBy { it.dtTxt.take(10) }
            .map { (dateStr, items) ->
                val label = try {
                    DateTimeFormatter.ofPattern("d/M E").format(LocalDate.parse(dateStr))
                } catch (e: Exception) {
                    dateStr
                }
                DailyForecast(
                    dateKey = dateStr,
                    dayLabel = label,
                    maxTemp = items.maxOf { it.main.tempMax },
                    minTemp = items.minOf { it.main.tempMin },
                    main = items.maxByOrNull { it.main.tempMax }
                        ?.weather?.firstOrNull()?.main ?: "Clear"
                )
            }
            .sortedBy { it.dateKey }
}
