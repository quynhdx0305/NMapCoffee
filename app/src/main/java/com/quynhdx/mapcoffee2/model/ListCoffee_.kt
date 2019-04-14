package com.quynhdx.mapcoffee2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListCoffee_ {

    @SerializedName("id")
    @Expose
    var id: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("vote")
    @Expose
    var vote: String? = null
    @SerializedName("number votate")
    @Expose
    var numberVotate: String? = null
    @SerializedName("background")
    @Expose
    var background: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("time open")
    @Expose
    var timeOpen: String? = null
    @SerializedName("web")
    @Expose
    var web: String? = null
    @SerializedName("image")
    @Expose
    var image: List<String>? = null
    @SerializedName("evaluate")
    @Expose
    var evaluate: List<Evaluate>? = null

}
