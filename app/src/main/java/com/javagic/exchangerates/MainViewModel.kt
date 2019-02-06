package com.javagic.exchangerates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javagic.exchangerates.ExchangeApp.Companion.api
import com.javagic.exchangerates.api.ExchangeItemApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

typealias ResData<T> = LiveData<ApiResponse<T>>
typealias MutableResData<T> = MutableLiveData<ApiResponse<T>>

class MainViewModel : ViewModel() {

    val disposable = CompositeDisposable()
    private val _exchangeList: MutableResData<List<ExchangeItemApi>> = MutableResData()
    val exchangeList: ResData<List<ExchangeItemApi>> = _exchangeList

    private val _pairs: MutableResData<List<String>> = MutableResData()
    val pairs: ResData<List<String>> = _pairs


    fun call() {
        api.pairs()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _pairs.postValue(ApiResponse.Success(it)) },
                { _pairs.postValue(ApiResponse.Error(it)) })
            .also { disposable.add(it) }
    }

}