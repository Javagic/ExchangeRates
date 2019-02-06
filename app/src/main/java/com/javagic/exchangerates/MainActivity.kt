package com.javagic.exchangerates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy { ViewModelProviders.of(this)[MainViewModel::class.java] }
    private val exchangeAdapter = ExchangeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        with(rvExchanges) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = itemAnimator
            adapter = exchangeAdapter
            addItemDecoration(ItemDecorator(R.drawable.divider_exchange))
        }
        viewModel.pairs.observeApiResponse({

        },{

        })
        btnClick.setOnClickListener {
            viewModel.call()
        }
    }

    protected fun <T> ResData<T>.observeApiResponse(onSuccess: (T) -> Unit, onError: (Throwable) -> Unit = {}) {
        observe(this@MainActivity, Observer {
            if (it == null) return@Observer
            when (it) {
                is ApiResponse.Success -> onSuccess(it.data)
                is ApiResponse.Error -> onError(it.exception)
            }
        })
    }
}
