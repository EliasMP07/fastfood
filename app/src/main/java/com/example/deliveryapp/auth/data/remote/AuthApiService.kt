package com.example.deliveryapp.auth.data.remote


import com.example.deliveryapp.auth.data.remote.dto.LoginResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): LoginResponse


    companion object {
        const val BASE_URL = "http://192.168.1.65:3000/api/"
    }
}