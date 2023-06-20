package pl.wader.taxometrapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import pl.wader.taxometrapp.databinding.FragmentDailyDrivingBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult

class DailyDrivingFragment : Fragment() {

    private var _binding: FragmentDailyDrivingBinding? = null
    private val binding get() = _binding!!
    private val mainVm by activityViewModels<MainViewModel>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var lastLocation: Location? = null
    private var distance: Double = 0.0

    private companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        _binding = FragmentDailyDrivingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @Suppress("DEPRECATION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        var EnterPrice = mainVm.DailyEnter
        var DrivingPrice = mainVm.DailyDriving

        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                Log.d("LOCATION_UPDATE", "Location updated")
                super.onLocationResult(result)
                Log.d("LOCATION_UPDATE2", "Location updated")
                for (location in result.locations){
                    if (lastLocation != null) {
                        Log.d("DISTANCE_UPDATE", "Distance updated")
                        distance += lastLocation!!.distanceTo(location) /1000
                        Log.d("DISTANCE_UPDATED", "Distance after update: $distance")
                        Log.d("LOCATION_UPDATE", "New location: ${location.latitude}, ${location.longitude}")
                    }
                    lastLocation = location
                    Log.d("LAST_LOCATION_UPDATE", "Last location updated")
                }

                // Aktualizujemy TextView1 każdorazowo, gdy dostajemy nową lokalizację.
                var EndPrice = EnterPrice+(DrivingPrice*distance)

                var FormattedEndPrice = String.format("%.2f", EndPrice)
                binding.TextView1.text = FormattedEndPrice
                mainVm.TempDailyDriving = EndPrice
            }
        }
        binding.EndDrivingButton.setOnClickListener {
            findNavController().navigate(R.id.action_dailyDrivingFragment_to_dailyEndScreenFragment)
        }


        startLocationUpdates()

        binding.WaitModeButton.setOnClickListener {
            findNavController().navigate(R.id.action_dailyDrivingFragment_to_dailyWaitingFragment)
            true
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            }
        }
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        _binding = null
    }
}