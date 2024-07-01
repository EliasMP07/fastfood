package com.example.deliveryapp.client.data.network

import com.example.deliveryapp.client.data.network.dto.category.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Header
interface ClientApiServices {
    @GET("category/getAll")
    suspend fun getAllCategories(
        @Header("Authorization") token: String
    ): List<CategoryDto>
}