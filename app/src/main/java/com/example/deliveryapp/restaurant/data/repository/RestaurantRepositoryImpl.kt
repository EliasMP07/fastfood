package com.example.deliveryapp.restaurant.data.repository

import android.content.Context
import com.example.deliveryapp.client.data.mapppers.toCategory
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.restaurant.data.mapper.toDeliveryAvailable
import com.example.deliveryapp.core.data.mapper.toOrderRestaurant
import com.example.deliveryapp.restaurant.data.remote.RestaurantApiService
import com.example.deliveryapp.core.data.remote.dto.DispatchedOrderRequest
import com.example.deliveryapp.restaurant.data.remote.requests.ProductRequest
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import com.example.deliveryapp.restaurant.domain.model.DeliveryAvailable
import com.example.deliveryapp.core.domain.model.order.Order
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestaurantRepositoryImpl(
    private val api: RestaurantApiService,
    private val sessionStorage: SessionStorage,
    private val context: Context
) : RestaurantRepository {


    override suspend fun createCategory(categoryRequest: CategoryRequest): Response<Unit> {
        return ApiCallHelper.safeCall {
            api.createCategory(
                token = sessionStorage.get()?.sessionToken ?: "",
                categoryRequest = categoryRequest
            )
            Unit
        }
    }

    override suspend fun getAllCategories(): Flow<List<Category>> {
        return flow {
            try {
                val apiResponse = api.getAllCategories(sessionStorage.get()?.sessionToken.orEmpty())
                val categories = apiResponse.map {
                    it.toCategory()
                }
                emit(categories)
            } catch (e: Exception) {
                emit(listOf())
                e.printStackTrace()
            }
        }
    }

    override suspend fun createProduct(
        name: String,
        images: List<String>,
        price: Double,
        description: String,
        idCategory: String
    ): Response<String> {
        return ApiCallHelper.safeCall {
            val response = api.createProduct(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                productRequest = ProductRequest(
                    name = name,
                    description = description,
                    price = price,
                    idCategory = idCategory,
                    image1 = images[0],
                    image2 = images[1],
                    image3 = images[2]
                )
            )
            response.message
        }
    }

    override suspend fun getAllOrders(status: String): Flow<Response<List<Order>>> {
        return ApiCallHelper.safeCallFlow {
            val response = api.getStatusOrdersClient(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                status = status
            )
            val orders = response.data?.map {
                it.toOrderRestaurant()
            }
            orders ?: emptyList()
        }
    }

    override suspend fun getAvailableDelivery(): Flow<Response<List<DeliveryAvailable>>> {
        return ApiCallHelper.safeCallFlow {
            val response = api.getDeliveryAvailable(
                token = sessionStorage.get()?.sessionToken.orEmpty()
            )
            val deliveries = response.data?.map {
                it.toDeliveryAvailable()
            }
            deliveries ?: emptyList()
        }
    }

    override suspend fun assignDelivery(
        idOrder: String,
        idClient: String,
        idAddress: String,
        idDelivery: String,
        status: String
    ): Response<Unit> {
        return ApiCallHelper.safeCall {
            api.assignDelivery(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                dispatchedOrderRequest = DispatchedOrderRequest(
                    idOrder = idOrder,
                    idDelivery = idDelivery,
                    idAddress = idAddress,
                    idClient = idClient,
                    status = status
                )
            )
        }
    }
}