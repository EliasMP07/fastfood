package com.example.deliveryapp.core.data.remote

import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

object ApiCallHelper {
    inline fun <T> safeApiCall(crossinline apiCall: suspend () -> T): Flow<Response<T>> {
        return flow {
            emit(Response.Loading)
            try {
                val result = apiCall()
                emit(Response.Success(result))
            } catch (e: HttpException) {
                val errorResponse: DeliveryApiResponse = parseErrorResponse(e)
                emit(Response.Failure(Exception(errorResponse.message)))
            } catch (e: UnknownHostException) {
                emit(Response.Failure(Exception("No Internet Connection")))
                e.printStackTrace()
            } catch (e: IOException) {
                emit(Response.Failure(Exception("Network Error")))
                e.printStackTrace()
            } catch (e: Exception) {
                emit(Response.Failure(e))
                e.printStackTrace()
            }
        }
    }

    suspend inline fun <T> safeApiCallNoFlow(crossinline apiCall: suspend () -> T): Response<T> {
        return try {
            val result = apiCall()
            Response.Success(result)
        } catch (e: HttpException) {
            val errorResponse: DeliveryApiResponse = parseErrorResponse(e)
            Response.Failure(Exception(errorResponse.message))
        } catch (e: UnknownHostException) {
            e.printStackTrace()
            Response.Failure(Exception("No Internet Connection"))
        } catch (e: IOException) {
            e.printStackTrace()
            Response.Failure(Exception("Network Error"))
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Failure(e)
        }
    }

}