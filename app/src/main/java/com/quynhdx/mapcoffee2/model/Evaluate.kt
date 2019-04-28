package com.quynhdx.mapcoffee2.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Evaluate (
    @SerializedName("user name")
    @Expose
    var userName: String? = null,
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null,
    @SerializedName("vote")
    @Expose
    var vote: String? = null,
    @SerializedName("comment")
    @Expose
    var comment: String? = null,
    @SerializedName("image")
    @Expose
    var image: List<String>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(avatar)
        parcel.writeString(vote)
        parcel.writeString(comment)
        parcel.writeStringList(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Evaluate> {
        override fun createFromParcel(parcel: Parcel): Evaluate {
            return Evaluate(parcel)
        }

        override fun newArray(size: Int): Array<Evaluate?> {
            return arrayOfNulls(size)
        }
    }

}
