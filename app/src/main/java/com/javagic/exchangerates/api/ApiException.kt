package com.javagic.exchangerates.api

import android.os.Parcel
import android.os.Parcelable

class ApiException(override val message: String) : Exception(message), Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ApiException> {
        override fun createFromParcel(parcel: Parcel): ApiException {
            return ApiException(parcel)
        }

        override fun newArray(size: Int): Array<ApiException?> {
            return arrayOfNulls(size)
        }
    }

}