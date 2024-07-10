package com.example.deliveryapp.auth.data.repository

import com.example.deliveryapp.auth.data.mapper.toRegisterRequestDto
import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.auth.domain.model.RegisterRequest
import com.example.deliveryapp.auth.domain.repository.AuthRepository
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.SessionStorage

class AuthRepositoryImp(
    private val api: AuthApiService,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): Response<User> {
        return ApiCallHelper.safeCall {
            val response = api.login(email = email, password = password)
            if (response.success && response.data != null){
                sessionStorage.set(response.data.toUser())
            }
            response.data?.toUser()?: User()
        }
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<Unit> {
        return ApiCallHelper.safeCall {
            val response = api.register(registerRequest.toRegisterRequestDto())
            if (response.success && response.data != null){
                sessionStorage.set(response.data.toUser())
            }
            Unit
        }
    }

}