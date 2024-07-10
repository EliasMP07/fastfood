package com.example.deliveryapp.client.data.network

import com.example.deliveryapp.client.data.network.dto.AddressDto
import com.example.deliveryapp.client.data.network.dto.address.AddressRequest
import com.example.deliveryapp.client.data.network.dto.category.CategoryDto
import com.example.deliveryapp.client.data.network.dto.category.ProductDto
import com.example.deliveryapp.client.data.network.dto.rating.RatingRequest
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ClientApiServices {
    @GET("category/getAll")
    suspend fun getAllCategories(
        @Header("Authorization") token: String
    ): List<CategoryDto>

    @GET("product/findByCategory/{id_category}")
    suspend fun getProductsByCategory(
        @Header("Authorization") token: String,
        @Path("id_category") idCategory: String
    ): DeliveryApiResponse<List<ProductDto>>

    @GET("product/getProductsPopular")
    suspend fun getProductsPopular(
        @Header("Authorization") token: String,
    ): List<ProductDto>

    @POST("product/rate")
    suspend fun addRatingProduct(
        @Header("Authorization") token: String,
        @Body ratingRequest: RatingRequest
    ): DeliveryApiResponse<Unit>

    @POST("address/create")
    suspend fun createAddress(
        @Header("Authorization") token: String,
        @Body addressRequest: AddressRequest
    ): DeliveryApiResponse<Unit>

    @GET("address/findByUser/{id_user}")
    suspend fun getAddressByUser(
        @Header("Authorization") token: String,
        @Path("id_user") idUser: String
    ): List<AddressDto>
}