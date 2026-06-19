package at.fh.kis.g1.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import at.fh.kis.g1.weatherapp.location.LocationManager
import at.fh.kis.g1.weatherapp.ui.WeatherUiState
import at.fh.kis.g1.weatherapp.ui.WeatherViewModel
import at.fh.kis.g1.weatherapp.ui.screens.WeatherScreen
import at.fh.kis.g1.weatherapp.ui.theme.WeatherAppTheme

class MainActivity : ComponentActivity() {

    private val locationManager by lazy { LocationManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
                val viewModel: WeatherViewModel = viewModel()
                val uiState by viewModel.uiState.collectAsState()
                val locationState by locationManager.location.collectAsState()

                val permissionLauncher = rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    if (permissions.values.any { it }) {
                        locationManager.startUpdates()
                    }
                }

                LaunchedEffect(Unit) {
                    if (hasLocationPermission()) {
                        locationManager.startUpdates()
                    }
                }

                LaunchedEffect(locationState) {
                    locationState?.let { loc ->
                        if (uiState is WeatherUiState.Empty) {
                            viewModel.loadWeatherForLocation(loc.latitude, loc.longitude)
                        }
                    }
                }

                WeatherScreen(
                    viewModel = viewModel,
                    onRequestLocationPermission = {
                        permissionLauncher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.stopUpdates()
    }

    private fun hasLocationPermission(): Boolean =
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
}
