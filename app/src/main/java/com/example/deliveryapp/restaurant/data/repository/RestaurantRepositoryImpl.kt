package com.example.deliveryapp.restaurant.data.repository

import android.content.Context
import com.example.deliveryapp.R
import com.example.deliveryapp.client.data.mapppers.toCategory
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.UiText
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.restaurant.data.remote.RestaurantApiService
import com.example.deliveryapp.restaurant.data.remote.dto.ProductRequest
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RestaurantRepositoryImpl(
    private val api: RestaurantApiService,
    private val sessionStorage: SessionStorage,
    private val context: Context
) : RestaurantRepository {


    override suspend fun createCategory(categoryRequest: CategoryRequest): Response<Unit> {
        return ApiCallHelper.safeApiCallNoFlow {
            val response = api.createCategory(
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
        return ApiCallHelper.safeApiCallNoFlow {
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
}