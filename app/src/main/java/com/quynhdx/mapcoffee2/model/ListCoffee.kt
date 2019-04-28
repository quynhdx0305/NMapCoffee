package com.quynhdx.mapcoffee2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ListCoffee (

    @SerializedName("list coffee")
    @Expose
    var listCoffee: ArrayList<ListCoffee_>? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(ListCoffee_)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListCoffee> {
        override fun createFromParcel(parcel: Parcel): ListCoffee {
            return ListCoffee(parcel)
        }

        override fun newArray(size: Int): Array<ListCoffee?> {
            return arrayOfNulls(size)
        }
    }
}
