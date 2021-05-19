package com.primasantosa.android.octomateprototype.view.timein

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.text.underline
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.primasantosa.android.octomateprototype.R
import com.primasantosa.android.octomateprototype.databinding.FragmentTimeInLocationBinding
import com.primasantosa.android.octomateprototype.util.UIUtil
import com.primasantosa.android.octomateprototype.util.UIUtil.showDialogTimeIn
import java.util.*

class TimeInLocationFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentTimeInLocationBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimeInLocationBinding.inflate(inflater, container, false)

        initUI()
        requestLocationPermission()

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    private fun initUI() {
        UIUtil.setToolbarTitle(activity, getString(R.string.label_time_in))
        UIUtil.hideDrawer(activity)
        binding.tvRecaptureLocation.text = SpannableStringBuilder().apply {
            append(getString(R.string.label_not_correct))
            underline { append(getString(R.string.label_recapture_location)) }
        }
        binding.tvRecaptureLocation.setOnClickListener {
            getCurrentLocation()
        }
        binding.btnCheckIn.setOnClickListener { showDialogTimeIn(requireContext(), ::goToProfile) }
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun goToProfile() {
        val direction =
            TimeInLocationFragmentDirections.actionTimeInLocationFragmentToProfileFragment()
        findNavController().navigate(direction)
    }

    private fun showPermissionError() {
        binding.btnCheckIn.visibility = View.GONE
        binding.tvRecaptureLocation.visibility = View.GONE
        binding.tvAccuracy.visibility = View.GONE
        binding.tcCurrentTime.visibility = View.GONE
        binding.tvLocationName.visibility = View.GONE

        Toast.makeText(
            requireContext(),
            getString(R.string.error_missing_location_permission),
            Toast.LENGTH_LONG
        ).show()
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                val acc = location.accuracy
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val address = geocoder.getFromLocation(lat, long, 1)
                binding.tvLocationName.text = address[0].getAddressLine(0)
                binding.tvAccuracy.text = StringBuilder().apply {
                    append(getString(R.string.label_current_location_acc))
                    append(acc)
                    append(getString(R.string.label_meter))
                }
                map.addMarker(
                    MarkerOptions().position(
                        LatLng(lat, long)
                    ).title(getString(R.string.label_current_position))
                )
                map.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(lat, long),
                        16.0f
                    )
                )
            }
        }
    }

    private fun requestLocationPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    getCurrentLocation()
                } else {
                    showPermissionError()
                }
            }

        when {
            isPermissionGranted() -> {
                getCurrentLocation()
            }
            shouldShowRequestPermissionRationale(REQUIRED_PERMISSIONS[0]) -> {
                showPermissionError()
            }
            else -> {
                requestPermissionLauncher.launch(
                    REQUIRED_PERMISSIONS[0]
                )
            }
        }
    }

    private fun isPermissionGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireContext(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
    }
}