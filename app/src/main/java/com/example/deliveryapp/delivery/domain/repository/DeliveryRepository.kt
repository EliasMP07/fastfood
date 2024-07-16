package com.example.deliveryapp.delivery.domain.repository

import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.domain.model.order.Order
import kotlinx.coroutines.flow.Flow

interface DeliveryRepository {
    suspend fun getMyOrdersAssign(status: String): Flow<Response<List<Order>>>
    suspend fun initDelivery(idOrder: String, idClient: String, idAddress: String, status: String): Response<Unit>
}