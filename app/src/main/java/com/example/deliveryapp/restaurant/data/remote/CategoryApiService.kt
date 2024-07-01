package com.example.deliveryapp.restaurant.data.remote

import com.example.deliveryapp.restaurant.data.remote.dto.CategoryDto
import com.example.deliveryapp.restaurant.data.remote.dto.CategoryResponse
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface CategoryApiService {

    @POST("category/create")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body categoryRequest: CategoryRequest
    ): CategoryResponse
}