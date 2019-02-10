package com.javagic.exchangerates.base

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<out T : ViewModel> : AppCompatActivity() {

    protected val viewModel: T by lazy {
        provideViewModel()
    }

    protected abstract fun provideViewModel(): T

    inline fun <reified T : ViewModel> AppCompatActivity.viewModel(): T =
        ViewModelProviders.of(this)[T::class.java]

    protected fun <T> LiveData<T>.observe(block: (T?) -> Unit) {
        observe(this@BaseActivity, Observer {
            block(it)
        })
    }
    protected fun <T> LiveData<T>.observeNotNull(block: (T) -> Unit) {
        observe(this@BaseActivity, Observer {
            if(it == null)return@Observer
            block(it)
        })
    }
    var View.visible: Boolean
        get() = visibility == View.VISIBLE
        set(value) {
            visibility = if (value) View.VISIBLE else View.GONE
        }
}
