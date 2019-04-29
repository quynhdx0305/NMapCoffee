package com.quynhdx.mapcoffee2.presenter

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.quynhdx.mapcoffee2.model.ListCoffee_

interface MapsPresenterItf {
    fun getMarker( marker: Marker, listDataCoffee: ArrayList<ListCoffee_> )
    fun setMarker( listDataCoffee: ArrayList<ListCoffee_>, mLocation: Location ): ArrayList<ListCoffee_>
    fun findMarkerById( id: String,  listDataCoffee: ArrayList<ListCoffee_> )
}