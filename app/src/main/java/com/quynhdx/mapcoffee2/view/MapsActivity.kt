package com.quynhdx.mapcoffee2.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
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
import com.quynhdx.mapcoffee2.model.ListCoffee_
import com.quynhdx.mapcoffee2.presenter.MapsPresenter
import com.quynhdx.mapcoffee2.presenter.MapsPresenterItf
import kotlinx.android.synthetic.main.activity_maps.*


private const val MY_PERMISSIONS_REQUEST_LOCATION = 99
private const val TAG = "MapsActivity"

enum class DirectionButton {
    Marker,
    NoMarker
}

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
    MapActivityItf, DirectionCallback, GoogleMap.OnMarkerClickListener {


    private lateinit var mMap: GoogleMap
    private lateinit var presenter: MapsPresenterItf

    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var origin: LatLng? = null // my location
    private var destination: LatLng? = null
    private val serverKey = "AIzaSyBmhgYek0liHgKHXnrkErK34YsEJRog2dk"

    var eButton = DirectionButton.NoMarker

    var listDataCoffee: ArrayList<ListCoffee_> = arrayListOf()
    var listDataCoffeeNear: ArrayList<ListCoffee_> = arrayListOf() // data new near my location

    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // TODO get data from Splash
        val intent = intent
        listDataCoffee = intent.getParcelableArrayListExtra("listDataCoffee")

        btn_request_list_coffee.setOnClickListener {
            val intent_ = Intent(this@MapsActivity, ListCoffeeActivity::class.java)
            intent_.putExtra("listDataCoffeeNear", listDataCoffeeNear)
            startActivity(intent_)
        }
        btn_request_direction.setOnClickListener {
            if (eButton == DirectionButton.Marker) {
                requestDirection()
            } else {
                Toast.makeText(this, "Chọn vị trí bạn muốn đến", Toast.LENGTH_SHORT).show()
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        MapsPresenter(this)
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
        mMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        if (marker != null) {
            presenter.getMarker(marker, listDataCoffeeNear)

            eButton = DirectionButton.Marker
            return true
        }
        return false
    }

    private var mLocationCallback: LocationCallback = object : LocationCallback() {

        override fun onLocationResult(locationResult: LocationResult) {

            val locationList = locationResult.locations

            if (locationList.count() > 0) {
                //The last location in the list is the newest
                val location: Location = locationList[locationList.count() - 1]
                Log.i("MapsActivity", "my Location: " + location.latitude + " " + location.longitude)

                val latLng = LatLng(location.latitude, location.longitude)
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
                mMap.animateCamera(cameraUpdate)
                // TODO update my location and set Marker near me
                origin = latLng
                listDataCoffeeNear = presenter.setMarker(listDataCoffee, location)

                Toast.makeText(this@MapsActivity, "longitude..." + location.longitude.toString(), Toast.LENGTH_LONG)
                    .show()
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

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Log.d("MapsActivity Permission access", "ACCESS_FINE_LOCATION")
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

    override fun showMarkDidTap(didTapCoffee: ListCoffee_) {
        //move camera
        val latLng = LatLng(didTapCoffee.latitude!!.toDouble(), didTapCoffee.longitude!!.toDouble())
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        mMap.animateCamera(cameraUpdate)

        destination = latLng
    }

    override fun loadMarks(location: LatLng, name: String?) {

        mMap.addMarker(
            MarkerOptions().position(location).title(name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.coffee))
        )
    }

    override fun setPresenter(presenter: MapsPresenterItf) {
        this.presenter = presenter
    }

    private fun requestDirection() {
        Toast.makeText(this@MapsActivity, "Direction Requesting...", Toast.LENGTH_SHORT).show()
        GoogleDirection.withServerKey(serverKey)
            .from(origin)
            .to(destination)
            .transportMode(TransportMode.DRIVING)
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

            val directionPositionList = route.legList[0].directionPoint
            mMap.addPolyline(DirectionConverter.createPolyline(this, directionPositionList, 5, Color.RED))
//            setCameraWithCoordinationBounds(route)
        } else {
            Log.d("MapsActivity onDirectionSuccess ....", direction.status)
        }
    }


    override fun onDirectionFailure(t: Throwable?) {
        Log.d("MapsActivity onDirectionFailure....", t?.message)
    }
}
