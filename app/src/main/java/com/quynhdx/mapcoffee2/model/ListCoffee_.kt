package com.quynhdx.mapcoffee2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ListCoffee_(

    @SerializedName("id")
    @Expose
    var id: String? = null,
    @SerializedName("name")
    @Expose
    var name: String? = null,
    @SerializedName("address")
    @Expose
    var address: String? = null,
    @SerializedName("vote")
    @Expose
    var vote: String? = null,
    @SerializedName("number votate")
    @Expose
    var numberVotate: String? = null,
    @SerializedName("background")
    @Expose
    var background: String? = null,
    @SerializedName("longitude")
    @Expose
    var longitude: String? = null,
    @SerializedName("latitude")
    @Expose
    var latitude: String? = null,
    @SerializedName("phone")
    @Expose
    var phone: String? = null,
    @SerializedName("time open")
    @Expose
    var timeOpen: String? = null,
    @SerializedName("web")
    @Expose
    var web: String? = null,
    @SerializedName("image")
    @Expose
    var image: List<String>? = null,
    @SerializedName("evaluate")
    @Expose
    var evaluate: List<Evaluate>? = null

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.createTypedArrayList(Evaluate)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(vote)
        parcel.writeString(numberVotate)
        parcel.writeString(background)
        parcel.writeString(longitude)
        parcel.writeString(latitude)
        parcel.writeString(phone)
        parcel.writeString(timeOpen)
        parcel.writeString(web)
        parcel.writeStringList(image)
        parcel.writeTypedList(evaluate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListCoffee_> {
        override fun createFromParcel(parcel: Parcel): ListCoffee_ {
            return ListCoffee_(parcel)
        }

        override fun newArray(size: Int): Array<ListCoffee_?> {
            return arrayOfNulls(size)
        }
    }
}
