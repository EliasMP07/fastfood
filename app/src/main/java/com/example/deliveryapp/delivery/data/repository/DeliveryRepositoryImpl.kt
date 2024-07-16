package com.example.deliveryapp.delivery.data.repository

import com.example.deliveryapp.core.data.mapper.toOrderRestaurant
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.data.remote.dto.DispatchedOrderRequest
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.delivery.data.network.DeliveryApiService
import com.example.deliveryapp.delivery.domain.repository.DeliveryRepository
import kotlinx.coroutines.flow.Flow

class DeliveryRepositoryImpl(
    private val api: DeliveryApiService,
    private val sessionStorage: SessionStorage
): DeliveryRepository{


    override suspend fun getMyOrdersAssign(status: String): Flow<Response<List<Order>>> {
        return ApiCallHelper.safeCallFlow {
            val response = api.getStatusOrderDelivery(
                token = sessionStorage.get()?.sessionToken?:"",
                status = status
            )
            response.data?.map {
                it.toOrderRestaurant()
            }?: emptyList()
        }
    }

    override suspend fun initDelivery(
        idOrder: String,
        idClient: String,
        idAddress: String,
        status: String
    ): Response<Unit> {
        return ApiCallHelper.safeCall {
            api.initDelivery(
                token = sessionStorage.get()?.sessionToken?:"",
                dispatchedOrderRequest = DispatchedOrderRequest(
                    idDelivery = sessionStorage.get()?.id?:"",
                    idAddress = idAddress,
                    idClient = idClient,
                    idOrder = idOrder,
                    status =  status
                )
            )
        }
    }
}