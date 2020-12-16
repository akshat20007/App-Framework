package com.example.trello.activities.models

import android.os.Parcel
import android.os.Parcelable

data class Tourni (
    val name: String = "",
    val banner: String = "",
    val mode: String = "",
    val slots: String = "",
    val device: String = "",
    val registered: ArrayList<String> = ArrayList(),
    var documentId: String = ""
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) = with(parcel) {
        parcel.writeString(name)
        parcel.writeString(banner)
        parcel.writeString(mode)
        parcel.writeString(slots)
        parcel.writeString(device)
        writeStringList(registered)
        parcel.writeString(documentId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Tourni> {
        override fun createFromParcel(parcel: Parcel): Tourni {
            return Tourni(parcel)
        }

        override fun newArray(size: Int): Array<Tourni?> {
            return arrayOfNulls(size)
        }
    }
}