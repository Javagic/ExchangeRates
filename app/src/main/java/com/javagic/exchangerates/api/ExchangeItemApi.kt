package com.javagic.exchangerates.api

import com.google.gson.annotations.SerializedName

class ExchangeItemApi(
    @SerializedName("symbol") val symbol: String?,
    @SerializedName("price") val price: Double,
    @SerializedName("bid") val bid: Double,
    @SerializedName("ask") val ask: Double,
    @SerializedName("timestamp") val timestamp: Long?
)