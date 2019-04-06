package com.quynhdx.mapcoffee2

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
private const val RADIUS_LARGE = 100.0
private const val STROKE_WIDTH = 1f

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapActivityItf {

    private lateinit var mMap: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }


    @SuppressLint("MissingPermission", "ObsoleteSdkInt")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        locationRequest = LocationRequest.create()
        locationRequest.interval = 120000
        locationRequest.fastestInterval = 120000
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                == PackageManager.PERMISSION_GRANTED
            ) {
                //Location Permission already granted
                fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
                mMap.isMyLocationEnabled = true
            } else {
                //Request Location Permission
                checkPermission()
            }
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
            mMap.isMyLocationEnabled = true
        }

        loadMarks()
    }

    private var mLocationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val locationList = locationResult.locations

            if (locationList.count() > 0) {
                //The last location in the list is the newest
                val location: Location = locationList[locationList.count() - 1]
                Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)

                //Move camera to last current location

//                mMap.clear()

                val latLng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                mMap.animateCamera(cameraUpdate)

                //presenter.startGetAddress(location.latitude.toString() + "," + location.longitude.toString())
                Toast.makeText(this@MapsActivity, "l..." + location.longitude.toString(), Toast.LENGTH_LONG).show()
//                mMap.addCircle(
//                    CircleOptions().apply {
//                        center(latLng )
//                        radius(RADIUS_LARGE)
//                        strokeWidth(STROKE_WIDTH)
//                        fillColor(ContextCompat.getColor(baseContext,R.color.colorCircleMap))
//                        strokeColor(ContextCompat.getColor(baseContext,R.color.colorCircleMap))
//                    })

            }
        }
    }

    private fun checkPermission() {

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )

                Log.d("Permission access", "First time")
            }
        } else {
            // Permission has already been granted
            Log.d("Permission access", "Permission has already been granted")

        }
    }

    override fun loadMarks() {
        val dhaka = LatLng(10.796456, 106.697287)
        mMap.addMarker(
            MarkerOptions().position(dhaka).title("Marker in Dhaka")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee))
        )
    }
}