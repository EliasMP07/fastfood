package com.example.deliveryapp.restaurant.data.repository

import android.content.Context
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.presentation.ui.UiText
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.restaurant.data.remote.RestaurantApiService
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import retrofit2.HttpException
import java.io.IOException

class RestaurantRepositoryImpl(
    private val api: RestaurantApiService,
    private val sessionStorage: SessionStorage,
    private val context: Context
): RestaurantRepository{

    override suspend fun createCategory(categoryRequest: CategoryRequest): Response<Unit> {
        return try {
            val response = api.createCategory(token = sessionStorage.get()?.sessionToken?:"", categoryRequest= categoryRequest)
            Response.Success(Unit)
        } catch (e: HttpException) {
            val errorResponse: DeliveryApiResponse = parseErrorResponse(e)
            Response.Failure(Exception(errorResponse.message))
        } catch (e: IOException) {
            Response.Failure(Exception(UiText.StringResource(R.string.error_network).asString(context)))
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}