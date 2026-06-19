package at.fh.kis.g1.weatherapp.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocationManager(context: Context) {

    private val fusedClient = LocationServices.getFusedLocationProviderClient(context)

    private val _location = MutableStateFlow<Location?>(null)
    val location: StateFlow<Location?> = _location.asStateFlow()

    private val request = LocationRequest.Builder(
        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
        10 * 60 * 1000L
    ).setMinUpdateIntervalMillis(5 * 60 * 1000L).build()

    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            _location.value = result.lastLocation
        }
    }

    @SuppressLint("MissingPermission")
    fun startUpdates() {
        fusedClient.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) _location.value = loc
        }
        fusedClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    fun stopUpdates() {
        fusedClient.removeLocationUpdates(callback)
    }
}
