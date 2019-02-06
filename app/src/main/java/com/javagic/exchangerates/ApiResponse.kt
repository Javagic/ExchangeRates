package com.javagic.exchangerates

sealed class ApiResponse<out T>(open val data: T?) {

    class Success<out T>(
        override val data: T
    ) : ApiResponse<T>(data)

    class Error(val exception: Throwable) : ApiResponse<Nothing>(null) {

        override fun toString(): String = exception.toString()

    }
}
