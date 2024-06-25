package com.example.deliveryapp.auth.data.repository

import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.auth.data.remote.dto.LoginResponse
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.auth.domain.repository.AuthRepository
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImp(
    private val api: AuthApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): Response<Unit> {
        return try {
            val response = api.login(email = email, password = password)
            if (response.success) {
                Response.Success(Unit)
            } else {
                Response.Failure(Exception(response.message))
            }
        } catch (e: HttpException) {
            val errorResponse = parseErrorResponse(e)
            Response.Failure(Exception(errorResponse.message))
        } catch (e: IOException) {
            Response.Failure(Exception("Network error"))
        } catch (e: Exception) {
            Response.Failure(e)
        }
    }

    private fun parseErrorResponse(e: HttpException): LoginResponse {
        return try {
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                Gson().fromJson(errorBody, LoginResponse::class.java)
            } else {
                LoginResponse(success = false, message = "Unknown error", userDto = null)
            }
        } catch (ex: Exception) {
            LoginResponse(success = false, message = "Error parsing error response", userDto = null)
        }
    }
}