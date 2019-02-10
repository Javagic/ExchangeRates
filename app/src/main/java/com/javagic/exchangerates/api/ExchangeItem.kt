package com.javagic.exchangerates.api

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class ExchangeItem(
    @SerializedName("symbol") val symbol: String,
    @SerializedName("price") var price: Double = 0.0,
    @SerializedName("bid") var bid: Double = 0.0,
    @SerializedName("ask") var ask: Double = 0.0,
    @SerializedName("timestamp") var timestamp: Long = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(symbol)
        parcel.writeDouble(price)
        parcel.writeDouble(bid)
        parcel.writeDouble(ask)
        parcel.writeLong(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExchangeItem> {
        override fun createFromParcel(parcel: Parcel): ExchangeItem {
            return ExchangeItem(parcel)
        }

        override fun newArray(size: Int): Array<ExchangeItem?> {
            return arrayOfNulls(size)
        }
    }

}