package com.example.deliveryapp.delivery.data.network

import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.dto.DispatchedOrderRequest
import com.example.deliveryapp.core.data.remote.dto.orders.OrderDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface DeliveryApiService {

    @GET("orders/findByStatus/{status}")
    suspend fun getStatusOrderDelivery(
        @Header("Authorization") token: String,
        @Path("status") status: String
    ): DeliveryApiResponse<List<OrderDto>>

    @PUT("orders/updateToOnTheWay")
    suspend fun initDelivery(
        @Header("Authorization") token: String,
        @Body dispatchedOrderRequest: DispatchedOrderRequest
    ): DeliveryApiResponse<Unit>

}