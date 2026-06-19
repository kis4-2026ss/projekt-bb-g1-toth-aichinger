package at.fh.kis.g1.weatherapp.data.repository

import android.content.Context
import android.location.Geocoder
import at.fh.kis.g1.weatherapp.BuildConfig
import at.fh.kis.g1.weatherapp.data.model.WeatherResponse
import at.fh.kis.g1.weatherapp.data.remote.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Locale

class WeatherRepository(private val context: Context) {

    private val api: WeatherApi by lazy {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        val client = OkHttpClient.Builder().addInterceptor(logging).build()
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    suspend fun getWeatherByLocation(lat: Double, lon: Double): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            runCatching {
                api.getHourlyForecast(lat, lon, BuildConfig.WEATHER_API_KEY, "metric")
            }
        }

    suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            runCatching {
                @Suppress("DEPRECATION")
                val addresses = Geocoder(context, Locale.getDefault())
                    .getFromLocationName(cityName, 1)
                    ?: throw IllegalArgumentException("Location not found: $cityName")
                if (addresses.isEmpty()) throw IllegalArgumentException("Location not found: $cityName")
                api.getHourlyForecast(
                    addresses[0].latitude,
                    addresses[0].longitude,
                    BuildConfig.WEATHER_API_KEY,
                    "metric"
                )
            }
        }
}
