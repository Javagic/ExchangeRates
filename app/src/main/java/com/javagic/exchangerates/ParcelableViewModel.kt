package com.javagic.exchangerates

import android.os.Bundle

interface ParcelableViewModel {
    fun writeTo(bundle: Bundle)
    fun readFrom(bundle: Bundle?)
    fun init()

}