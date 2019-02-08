package com.javagic.exchangerates

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.javagic.exchangerates.api.ApiException
import com.javagic.exchangerates.api.ExchangeItem
import com.javagic.exchangerates.api.ExchangeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

private const val EXCHANGE_LIST_EXTRA = "MainViewModel.ExchangeList"

class MainViewModel : ViewModel(), ParcelableViewModel {
    override fun init() {
        loadSymbols()
    }

    private val disposable = CompositeDisposable()
    private val _exchangeList: MutableLiveData<ArrayList<ExchangeItem>> = MutableLiveData()
    val exchangeList: LiveData<ArrayList<ExchangeItem>> = _exchangeList

    private val _error: MutableLiveData<ApiException?> = MutableLiveData()
    val error: LiveData<ApiException?> = _error

    override fun writeTo(bundle: Bundle) {
        bundle.putParcelableArrayList(EXCHANGE_LIST_EXTRA, _exchangeList.value!!)
    }

    override fun readFrom(bundle: Bundle?) {
        bundle?.apply {
            getParcelableArrayList<ExchangeItem>(EXCHANGE_LIST_EXTRA)
                .also(_exchangeList::postValue)
        }
    }

    fun loadSymbols() {
        ExchangeRepository.symbols()
            .subscribe({
                _exchangeList.postValue(ArrayList(it))
                _error.postValue(null)
            },
                { _error.postValue(it as ApiException) })
            .also { disposable.add(it) }
    }

    fun requestQuotes(list: IntRange) =
        _exchangeList.value!!.slice(list)
            .joinToString(",") { it.symbol }
            .let { ExchangeRepository.quotes(it) }
            .doOnSuccess { newList ->
                list.forEach {
                    _exchangeList.value!![it] = newList[it - list.first]
                }
            }
            .observeOn(AndroidSchedulers.mainThread())


    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

}