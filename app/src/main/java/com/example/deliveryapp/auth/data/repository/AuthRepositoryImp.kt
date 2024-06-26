package com.example.deliveryapp.auth.data.repository

import android.content.Context
import com.example.deliveryapp.R
import com.example.deliveryapp.auth.data.mapper.toRegisterRequestDto
import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.auth.data.remote.dto.AuthResponse
import com.example.deliveryapp.auth.domain.model.RegisterRequest
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.auth.domain.repository.AuthRepository
import com.example.deliveryapp.core.presentation.ui.UiText
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImp(
    private val api: AuthApiService,
    private val context: Context
) : AuthRepository {

    override suspend fun login(email: String, password: String): Response<Unit> {
        return try {
            val response = api.login(email = email, password = password)
            Response.Success(Unit)
        } catch (e: HttpException) {
            val errorResponse: AuthResponse = parseErrorResponse(e)
            Response.Failure(Exception(errorResponse.message))
        } catch (e: IOException) {
            Response.Failure(Exception(UiText.StringResource(R.string.error_network).asString(context)))
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private inline fun <reified T> parseErrorResponse(exception: HttpException): T {
        val errorBody = exception.response()?.errorBody()?.string()
        return Gson().fromJson(errorBody, T::class.java)
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<Unit> {
        return try {
            val response = api.register(registerRequest.toRegisterRequestDto())
            Response.Success(Unit)
        } catch (e: HttpException) {
            val errorResponse: AuthResponse = parseErrorResponse(e)
            Response.Failure(Exception(errorResponse.message))
        } catch (e: IOException) {
            Response.Failure(Exception(UiText.StringResource(R.string.error_network).asString(context)))
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }
}