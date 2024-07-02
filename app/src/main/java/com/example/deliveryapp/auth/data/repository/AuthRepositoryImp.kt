package com.example.deliveryapp.auth.data.repository

import android.content.Context
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.data.mapper.toRegisterRequestDto
import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.auth.domain.model.RegisterRequest
import com.example.deliveryapp.auth.domain.repository.AuthRepository
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.core.presentation.ui.UiText
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImp(
    private val api: AuthApiService,
    private val context: Context,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): Response<User> {
        return ApiCallHelper.safeApiCallNoFlow {
            val response = api.login(email = email, password = password)
            if (response.success && response.userDto != null){
                sessionStorage.set(response.userDto.toUser())
            }
            response.userDto?.toUser()?: User()
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<Unit> {
        return ApiCallHelper.safeApiCallNoFlow {
            val response = api.register(registerRequest.toRegisterRequestDto())
            if (response.success && response.userDto != null){
                sessionStorage.set(response.userDto.toUser())
            }
            Unit
        }
    }

}