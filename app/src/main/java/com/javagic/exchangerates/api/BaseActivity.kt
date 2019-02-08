package com.javagic.exchangerates.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BaseActivity<out T : ViewModel> : AppCompatActivity() {

    protected val viewModel: T by lazy {
        provideViewModel()
    }

    protected abstract fun provideViewModel(): T

    protected inline fun <reified T : ViewModel> AppCompatActivity.viewModelBundleAware(bundle: Bundle? = intent.extras): T =
        ViewModelProviders.of(
            this,
            BundleAwareViewModelFactory(bundle, ViewModelProvider.NewInstanceFactory())
        )[T::class.java]

}
