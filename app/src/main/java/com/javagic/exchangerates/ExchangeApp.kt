package com.javagic.exchangerates

import android.app.Application
import com.javagic.exchangerates.api.ForexApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val FOREX_URL = "https://forex.1forge.com/"
const val API_VERSION = "1.0.3"
const val API_KEY = "api_key"

class ExchangeApp : Application() {

    companion object {
        lateinit var instance: ExchangeApp
            private set

        lateinit var api: ForexApi
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Timber.plant(Timber.DebugTree())
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                chain.request().run {
                    val newUrl = url().newBuilder().addQueryParameter(API_KEY, BuildConfig.TOKEN).build()
                    newBuilder().url(newUrl).build().let { chain.proceed(it) }
                }
            }
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(FOREX_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(ForexApi::class.java)
    }
}