package com.quynhdx.mapcoffee2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListCoffee {

    @SerializedName("list coffee")
    @Expose
    var listCoffee: ArrayList<ListCoffee_>? = null

}
