package com.javagic.exchangerates.main

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.SimpleItemAnimator
import com.javagic.exchangerates.ExchangeApp
import com.javagic.exchangerates.R
import com.javagic.exchangerates.base.BaseActivity
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit

open class MainActivity : BaseActivity<MainViewModel>() {
    override fun provideViewModel(): MainViewModel = viewModel()

    private val exchangeAdapter = ExchangeAdapter()
    private val layoutManager by lazy {
        LinearLayoutManager(this@MainActivity)
    }

    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) viewModel.init()
        with(rvExchanges) {
            setHasFixedSize(true)
            layoutManager = this@MainActivity.layoutManager
            itemAnimator = DefaultItemAnimator()
            adapter = exchangeAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, VERTICAL).apply {
                setDrawable(
                    ContextCompat.getDrawable(
                        ExchangeApp.instance,
                        R.drawable.divider_exchange
                    asdasdad
                    )!!
                )
            })
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        with(viewModel) {
            error.observe {
                if (it != null) {
                    tvError.text = it.message
                    tvError.visible = true
                    btnRetry.visible = true
                } else {
                    tvError.visible = false
                    btnRetry.visible = false
                }
            }
            btnRetry.setOnClickListener {
                loadSymbols()
            }
        }

        viewModel.exchangeList.observeNotNull {
            exchangeAdapter.submitList(it)
            startObserving()
        }
    }


    private fun startObserving() {
        Observable.interval(2, TimeUnit.SECONDS)
            .subscribe {
                with(layoutManager) {
                    quotes(findFirstVisibleItemPosition()..findLastVisibleItemPosition())
                }
            }
            .also { disposable.add(it) }
    }

    private fun quotes(intRange: IntRange) {
        Timber.i(intRange.toString())
        if (intRange.first == NO_POSITION) return
        viewModel.requestQuotes(intRange)
            .subscribe({ newList ->
                intRange.forEach { exchangeAdapter.data[it] = newList[it - intRange.first] }
                exchangeAdapter.notifyItemRangeChanged(intRange.first, intRange.count())
            }, Throwable::printStackTrace)
            .also { disposable.add(it) }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.let {
            viewModel.writeTo(it)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            viewModel.readFrom(it)
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
    }

    override fun onStop() {
        super.onStop()
        disposable.clear()
    }

    override fun onStart() {
        super.onStart()
        if (exchangeAdapter.data.isNotEmpty()) {
            startObserving()
        }
    }

}
