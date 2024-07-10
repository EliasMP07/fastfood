package com.example.deliveryapp.core.user.data.network

import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.user.data.network.dto.UserDto
import com.example.deliveryapp.core.user.data.network.dto.UserRequestDto
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT

interface UserApiService {

    @PUT("users/update")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body userRequestDto: UserRequestDto
    ): DeliveryApiResponse<UserDto>
}