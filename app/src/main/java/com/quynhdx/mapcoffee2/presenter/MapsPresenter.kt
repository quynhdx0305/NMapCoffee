package com.quynhdx.mapcoffee2.presenter

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.quynhdx.mapcoffee2.model.ListCoffee_
import com.quynhdx.mapcoffee2.view.MapActivityItf

private const val TAG = "MapsPresenter"

class MapsPresenter(val view : MapActivityItf) : MapsPresenterItf{

    init {
        view.setPresenter(this)
    }

    override fun setMarker(listDataCoffee: ArrayList<ListCoffee_>, mLocation: Location) {
        for ( mCoffee in listDataCoffee ) {
            val longitude = mCoffee.longitude!!.toDouble()
            val latitude = mCoffee.latitude!!.toDouble()
            val name = mCoffee.name

            val coffeeLocation = Location("")
            coffeeLocation.longitude = longitude
            coffeeLocation.latitude = latitude
            val far = mLocation.distanceTo(coffeeLocation) / 1000
            Log.d(TAG, far.toString())
            if( far <= 5.0) {
                //TODO far <= 5km
                view.loadMarks(LatLng(latitude,longitude), name)
            }
        }
    }
}