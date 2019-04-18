package com.quynhdx.mapcoffee2.presenter

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.quynhdx.mapcoffee2.model.ListCoffee_
import com.quynhdx.mapcoffee2.view.MapActivityItf


class MapsPresenter(val view : MapActivityItf) : MapsPresenterItf{
    init {
        view.setPresenter(this)
    }

    var listDataCoffeeNear : ArrayList<ListCoffee_> = arrayListOf() // data new near my location

    override fun getMarker(marker: Marker, listDataCoffee: ArrayList<ListCoffee_>) {

        for ( coffeeLocation in listDataCoffee ) {
            val latLagCoffee = LatLng(coffeeLocation.latitude!!.toDouble(), coffeeLocation.longitude!!.toDouble())
            if (marker.position == latLagCoffee) {
                view.showMarkDidTap(coffeeLocation)
            }
        }
    }

    override fun setMarker(listDataCoffee: ArrayList<ListCoffee_>, mLocation: Location): ArrayList<ListCoffee_> {

        for ( mCoffee in listDataCoffee ) {
            val longitude = mCoffee.longitude!!.toDouble()
            val latitude = mCoffee.latitude!!.toDouble()
            val name = mCoffee.name

            val coffeeLocation = Location("")
            coffeeLocation.longitude = longitude
            coffeeLocation.latitude = latitude
            val far = mLocation.distanceTo(coffeeLocation) / 1000

            if( far <= 5.0) {
                //TODO far <= 5km and create listDataCoffeeNear new data
                listDataCoffeeNear.add(mCoffee)
                view.loadMarks(LatLng(latitude,longitude), name)
            }
        }

        return listDataCoffeeNear
    }
}