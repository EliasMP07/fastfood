package com.example.deliveryapp.restaurant.data.remote

import com.example.deliveryapp.client.data.network.dto.category.CategoryDto
import com.example.deliveryapp.restaurant.data.remote.dto.CategoryResponse
import com.example.deliveryapp.restaurant.data.remote.dto.ProductCreateResponse
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import com.example.deliveryapp.restaurant.data.remote.dto.ProductRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface RestaurantApiService {

    @POST("category/create")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body categoryRequest: CategoryRequest
    ): CategoryResponse

    @GET("category/getAll")
    suspend fun getAllCategories(
        @Header("Authorization") token: String
    ): List<CategoryDto>

    @POST("product/create")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body productRequest: ProductRequest
    ): ProductCreateResponse
}