package com.example.deliveryapp.auth.data.remote


import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.user.data.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthApiService {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(@Field("email") email: String, @Field("password") password: String): DeliveryApiResponse<UserDto>

    @POST("users/create")
    suspend fun register(@Body registerRequestDto: RegisterRequestDto): DeliveryApiResponse<UserDto>

    companion object {
        const val BASE_URL = "http://192.168.1.71:3000/v1/"
    }
}