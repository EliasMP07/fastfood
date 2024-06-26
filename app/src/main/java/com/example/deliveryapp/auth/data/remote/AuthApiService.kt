package com.example.deliveryapp.auth.data.remote


import com.example.deliveryapp.auth.data.remote.dto.AuthResponse
import com.example.deliveryapp.auth.data.remote.dto.register.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): AuthResponse

    @POST("users/create")
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): AuthResponse


    companion object {
        const val BASE_URL = "http://192.168.1.66:3000/api/"
    }
}