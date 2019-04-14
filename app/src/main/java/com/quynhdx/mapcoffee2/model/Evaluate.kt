package com.quynhdx.mapcoffee2.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Evaluate {

    @SerializedName("user name")
    @Expose
    var userName: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null
    @SerializedName("vote")
    @Expose
    var vote: String? = null
    @SerializedName("comment")
    @Expose
    var comment: String? = null
    @SerializedName("image")
    @Expose
    var image: List<String>? = null

}
