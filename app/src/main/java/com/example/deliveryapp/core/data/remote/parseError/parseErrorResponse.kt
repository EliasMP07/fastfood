package com.example.deliveryapp.core.data.remote.parseError

import com.google.gson.Gson
import retrofit2.HttpException

inline fun <reified T> parseErrorResponse(exception: HttpException): T {
    val errorBody = exception.response()?.errorBody()?.string()
    return Gson().fromJson(errorBody, T::class.java)
}