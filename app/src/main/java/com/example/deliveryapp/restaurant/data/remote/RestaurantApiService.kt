package com.example.deliveryapp.restaurant.data.remote

import com.example.deliveryapp.client.data.network.dto.category.CategoryDto
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.dto.DispatchedOrderRequest
import com.example.deliveryapp.core.data.remote.dto.orders.OrderDto
import com.example.deliveryapp.restaurant.data.remote.dto.DeliveryAvailableDto
import com.example.deliveryapp.restaurant.data.remote.requests.ProductRequest
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RestaurantApiService {

    @POST("category/create")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body categoryRequest: CategoryRequest
    ): DeliveryApiResponse<String>

    @GET("category/getAll")
    suspend fun getAllCategories(
        @Header("Authorization") token: String
    ): List<CategoryDto>

    @POST("product/create")
    suspend fun createProduct(
        @Header("Authorization") token: String,
        @Body productRequest: ProductRequest
    ): DeliveryApiResponse<Void>

    @GET("orders/findByStatus/{status}")
    suspend fun getStatusOrdersClient(
        @Header("Authorization") token: String,
        @Path("status") status: String
    ): DeliveryApiResponse<List<OrderDto>>

    @GET("users/findDeliveryMen")
    suspend fun getDeliveryAvailable(
        @Header("Authorization") token: String,
    ): DeliveryApiResponse<List<DeliveryAvailableDto>>

    @PUT("orders/updateToDispatched")
    suspend fun assignDelivery(
        @Header("Authorization") token: String,
        @Body dispatchedOrderRequest: DispatchedOrderRequest
    ): DeliveryApiResponse<Unit>

}