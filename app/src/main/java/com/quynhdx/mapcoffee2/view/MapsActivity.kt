package com.quynhdx.mapcoffee2.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.akexorcist.googledirection.DirectionCallback
import com.akexorcist.googledirection.GoogleDirection
import com.akexorcist.googledirection.constant.TransportMode
import com.akexorcist.googledirection.model.Direction
import com.akexorcist.googledirection.util.DirectionConverter
import com.google.android.gms.location.*

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.quynhdx.mapcoffee2.R


private const val MY_PERMISSIONS_REQUEST_LOCATION = 99

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, MapActivityItf, DirectionCallback {

    private var btnRequestDirection: Button? = null

    private lateinit var mMap: GoogleMap
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var place1: MarkerOptions? = null
    private var place2: MarkerOptions? = null
    private var origin = LatLng(10.790456, 106.690287)
    private var destination = LatLng(10.794456, 106.694287)

    private val serverKey = "AIzaSyBmhgYek0liHgKHXnrkErK34YsEJRog2dk"

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        btnRequestDirection = findViewById(R.id.btn_request_direction)
        btnRequestDirection!!.setOnClickListener {
            requestDirection()
        }

        place1 = MarkerOptions().position(origin).title("Location 1")
        place2 = MarkerOptions().position(destination).title("Location 2")

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
                checkPermission()
            }
        } else {
            fusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper())
            mMap.isMyLocationEnabled = true
        }

//        loadMarks()
//        Timer("SettingUp", false).schedule(5000) {
//            requestDirection()
//        }
    }

    private var mLocationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val locationList = locationResult.locations

            if (locationList.count() > 0) {
                //The last location in the list is the newest
                val location: Location = locationList[locationList.count() - 1]
                Log.i("MapsActivity", "Location: " + location.latitude + " " + location.longitude)

                val latLng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                mMap.animateCamera(cameraUpdate)
                // update origin
                origin = latLng

                Toast.makeText(this@MapsActivity, "longitude..." + location.longitude.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPermission() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {

            if ( ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION )
            ) {
                Log.d("MapsActivity Permission access", "mmmmmmmmmm")
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )

                Log.d("MapsActivity Permission access", "First time")
            }
        } else {
            // Permission has already been granted
            Log.d("MapsActivity Permission access", "Permission has already been granted")

        }
    }

    override fun loadMarks() {
        mMap.addMarker(place1)
        mMap.addMarker(place2)

//        Log.d("loadMarks", getUrl(place1!!.position, place2!!.position, "driving"))
//        val dhaka = LatLng(10.796456, 106.697287)
//        mMap.addMarker(
//            MarkerOptions().position(dhaka).title("Marker in Dhaka")
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee))
//        )
//
//        val line = mMap.addPolyline(
//            PolylineOptions()
//                .add(dhaka, LatLng(10.716356, 106.647287))
//                .width(5f)
//                .color(Color.RED)
//        )
    }

    private fun requestDirection() {
        Toast.makeText(this@MapsActivity, "Direction Requesting...", Toast.LENGTH_SHORT).show()
        GoogleDirection.withServerKey(serverKey)
            .from(origin)
            .to(destination)
            .transportMode(TransportMode.WALKING)
            .execute(this)
    }

    override fun onDirectionSuccess(direction: Direction?, rawBody: String?) {
        if (direction == null) {
            Log.d("MapsActivity onDirectionSuccess direction....", "null")
            return
        }
        Toast.makeText(this@MapsActivity, "Success with status : " + direction.status, Toast.LENGTH_SHORT).show()
        if (direction.isOK) {
            val route = direction.routeList[0]
//            mMap.addMarker(place1)
            mMap.addMarker(place2)

            val directionPositionList = route.legList[0].directionPoint
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED))
//            setCameraWithCoordinationBounds(route)
            btnRequestDirection!!.visibility = View.GONE
        } else {
            Log.d("MapsActivity onDirectionSuccess ...." , direction.status)
        }
    }


    override fun onDirectionFailure(t: Throwable?) {
        Log.d("MapsActivity onDirectionFailure....", t?.message)
    }
}