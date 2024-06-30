package com.example.deliveryapp.core.user.data.repository

import android.content.Context
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.user.data.mapper.toUserRequestDto
import com.example.deliveryapp.core.user.data.network.UserApiService
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.core.user.domain.repository.UserRepository
import com.example.deliveryapp.core.presentation.ui.UiText
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImp(
    private val context: Context,
    private val api: UserApiService,
    private val sessionStorage: SessionStorage
): UserRepository {
    override suspend fun update(user: User): Response<User> {
        return try {
            val response = api.updateProfile(userRequestDto = user.toUserRequestDto(), token = user.sessionToken)
            if (response.success && response.userDto != null){
                sessionStorage.set(response.userDto.toUser())
            }
            Response.Success(response.userDto!!.toUser())
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