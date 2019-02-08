package com.javagic.exchangerates.api

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.javagic.exchangerates.ParcelableViewModel
import timber.log.Timber


class BundleAwareViewModelFactory(
    private val bundle: Bundle?,
    private val provider: ViewModelProvider.Factory
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        Timber.i("create viewModel %s", bundle.toString())
        val viewModel = provider.create(modelClass)
        return viewModel
    }
}