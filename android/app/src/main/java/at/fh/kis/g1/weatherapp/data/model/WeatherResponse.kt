package at.fh.kis.g1.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val cod: String,
    val message: Double?,
    val cnt: Int?,
    val list: List<ListItem>,
    val city: CityData
)

data class ListItem(
    val dt: Long,
    val main: MainData,
    val weather: List<WeatherItem>,
    val clouds: CloudsData,
    val wind: WindData,
    val visibility: Double,
    val pop: Double,
    val rain: RainData?,
    val sys: SysData,
    @SerializedName("dt_txt") val dtTxt: String
)

data class MainData(
    val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    val pressure: Double,
    @SerializedName("sea_level") val seaLevel: Double?,
    @SerializedName("grnd_level") val groundLevel: Double?,
    val humidity: Double,
    @SerializedName("temp_kf") val tempKf: Double?
)

data class WeatherItem(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class CloudsData(val all: Double)

data class WindData(
    val speed: Double,
    val deg: Double,
    val gust: Double?
)

data class RainData(
    @SerializedName("1h") val oneHour: Double
)

data class SysData(val pod: String)

data class CityData(
    val id: Long,
    val name: String,
    val coord: Coordinates,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long
)

data class Coordinates(
    val lat: Double?,
    val lon: Double?
)
