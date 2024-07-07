package com.example.deliveryapp.core.data.remote

import com.example.deliveryapp.R
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

/**
 * Helper object for making safe calls using coroutines.
 */
object ApiCallHelper {

    /**
     * Executes a safe call wrapped in a Flow.
     * @param apiCall Suspended function representing the call.
     * @return Flow<Response<T>> emitting Loading, Success or Failure states.
     */
    inline fun <T> safeCallFlow(crossinline apiCall: suspend () -> T): Flow<Response<T>> {
        return flow {
            emit(Response.Loading)
            try {
                val result = apiCall()
                emit(Response.Success(result))
            } catch (e: HttpException) {
                handleHttpException(this, e)
            } catch (e: UnknownHostException) {
                handleNetworkError(this)
            } catch (e: ConnectException) {
                handleNetworkError(this) // Maneja ConnectException como un error de red
            } catch (e: Exception) {
                handleGeneralError(this, e)
            }
        }
    }


    /**
     * Executes a safe call without using Flow.
     * @param apiCall Suspended function representing the call.
     * @return Response<T> representing Success or Failure state.
     */
    suspend inline fun <T> safeCall(crossinline apiCall: suspend () -> T): Response<T> {
        return try {
            val result = apiCall()
            Response.Success(result)
        } catch (e: HttpException) {
            handleHttpException(e)
        }
        catch (e: UnknownHostException) {
            Response.Failure(UiText.StringResource(R.string.error_network))
        } catch (e: ConnectException) {
            Response.Failure(UiText.StringResource(R.string.error_connect_exception))
        } catch (e: Exception) {
            Response.Failure(UiText.DynamicString(e.toString()))
        }
    }

    /**
     * Handles HTTP exceptions.
     * @param collector FlowCollector emitting the response.
     * @param e HttpException thrown by Retrofit.
     */
    suspend fun <T> handleHttpException(collector: FlowCollector<Response<T>>, e: HttpException) {
        val errorResponse: DeliveryApiResponse = parseErrorResponse(e)
        val errorMessage = when(e.code()){
            408 ->  UiText.StringResource(R.string.request_timeout)
            413 ->  UiText.StringResource(R.string.payload_too_large)
            in 500..599 ->  UiText.StringResource(R.string.error_server)
            else ->   UiText.DynamicString(errorResponse.message)
        }
        collector.emit(Response.Failure(errorMessage))
    }

    /**
     * Handles HTTP exceptions without FlowCollector.
     * @param e HttpException thrown by Retrofit.
     */
    fun <T> handleHttpException(e: HttpException): Response<T> {
        val errorResponse: DeliveryApiResponse = parseErrorResponse(e)

        return when(e.code()){
            408 ->  Response.Failure(UiText.StringResource(R.string.request_timeout))
            413 ->  Response.Failure(UiText.StringResource(R.string.payload_too_large))
            in 500..599 ->  Response.Failure(UiText.StringResource(R.string.error_server))
            else ->  Response.Failure(UiText.DynamicString(errorResponse.message))
        }
    }

    /**
     * Handles network connectivity issues (UnknownHostException, IOException).
     * @param collector FlowCollector emitting the response.
     */
    suspend fun <T> handleNetworkError(collector: FlowCollector<Response<T>>) {
        collector.emit(Response.Failure(UiText.StringResource(R.string.error_network)))
    }

    /**
     * Handles general exceptions.
     * @param collector FlowCollector emitting the response.
     * @param e Exception thrown.
     */
   suspend fun <T> handleGeneralError(collector: FlowCollector<Response<T>>, e: Exception) {
        e.printStackTrace()
        collector.emit(Response.Failure(UiText.DynamicString(e.toString())))
    }
}