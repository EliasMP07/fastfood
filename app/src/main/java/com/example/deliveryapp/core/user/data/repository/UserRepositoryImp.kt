package com.example.deliveryapp.core.user.data.repository

import android.content.Context
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.user.data.mapper.toUserRequestDto
import com.example.deliveryapp.core.user.data.network.UserApiService
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.core.user.domain.repository.UserRepository

class UserRepositoryImp(
    private val context: Context,
    private val api: UserApiService,
    private val sessionStorage: SessionStorage
): UserRepository {
    override suspend fun update(user: User): Response<User> {
        return ApiCallHelper.safeApiCallNoFlow {
            val response = api.updateProfile(userRequestDto = user.toUserRequestDto(), token = user.sessionToken)
            if (response.success && response.userDto != null){
                sessionStorage.set(response.userDto.toUser())
            }
            response.userDto!!.toUser()
        }
    }
}