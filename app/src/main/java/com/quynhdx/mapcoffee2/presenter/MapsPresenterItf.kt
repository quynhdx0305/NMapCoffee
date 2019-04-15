package com.quynhdx.mapcoffee2.presenter

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.quynhdx.mapcoffee2.model.ListCoffee_

interface MapsPresenterItf {
    fun setMarker(listDataCoffee: ArrayList<ListCoffee_>, mLocation: Location)
}