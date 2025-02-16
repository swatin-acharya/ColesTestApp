package com.example.colestestapp.error

const val error_message_incorrect_data = "Incorrect format data"
const val error_message_no_file = "Data file not found"

enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING
}

sealed class ApiResult <out T> (val status: ApiStatus, val data: T?, val message:String?) {

    data class Success<out R>(val _data: R?): ApiResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: String): ApiResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )

    data class Loading<out R>(val isLoading: Boolean): ApiResult<R>(
        status = ApiStatus.LOADING,
        data = null,
        message = null
    )
}