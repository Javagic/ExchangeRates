package com.javagic.exchangerates.api

import com.javagic.exchangerates.API_VERSION
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ForexApi {

    @GET("$API_VERSION/quotes")
    fun quotes(@Query("pairs") pairs: String): Single<List<ExchangeItemApi>>

    @GET("$API_VERSION/symbols")
    fun pairs(): Single<List<String>>

}