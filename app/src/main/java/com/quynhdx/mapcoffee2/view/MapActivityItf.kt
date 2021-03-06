package com.quynhdx.mapcoffee2.view

import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.quynhdx.mapcoffee2.model.ListCoffee_
import com.quynhdx.mapcoffee2.presenter.MapsPresenterItf

interface MapActivityItf {
    fun setPresenter(presenter: MapsPresenterItf)
    fun loadMarks(location: LatLng, name: String?)
    fun showMarkDidTap(didTapCoffee : ListCoffee_)
    fun requestDirection()
}