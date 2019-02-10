package com.javagic.exchangerates.api

import com.javagic.exchangerates.ExchangeApp.Companion.api
import com.javagic.exchangerates.ExchangeApp.Companion.instance
import com.javagic.exchangerates.R
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object ExchangeRepository {

    fun symbols(): Single<List<ExchangeItem>> =
        api.pairs()
            .subscribeOn(Schedulers.io())
            .map { it.map { ExchangeItem(it) } }
            .checkExceptions()

    fun quotes(pair: String): Single<List<ExchangeItem>> =
        api.quotes(pair)
            .subscribeOn(Schedulers.io())

    private fun <T> Single<T>.checkExceptions() =
        onErrorResumeNext { throwable: Throwable ->
            when (throwable) {
                is UnknownHostException, is ConnectException, is SocketTimeoutException -> R.string.error_connection
                is retrofit2.HttpException -> R.string.error_server
                else -> R.string.error_unexpected
            }
                .let { Single.error(ApiException(instance.getString(it))) }
        }

}