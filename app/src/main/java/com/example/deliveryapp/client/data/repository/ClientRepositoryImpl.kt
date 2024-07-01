package com.example.deliveryapp.client.data.repository

import android.content.Context
import com.example.deliveryapp.auth.domain.model.Response
import com.example.deliveryapp.client.data.mapppers.toCategory
import com.example.deliveryapp.client.data.network.ClientApiServices
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.ResponseClient
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.data.remote.dto.DeliveryApiResponse
import com.example.deliveryapp.core.data.remote.parseError.parseErrorResponse
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

class ClientRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val api: ClientApiServices,
    private val context: Context
): ClientRepository{
    override suspend fun getAllCategories(): Flow<ResponseClient<List<Category>>> {
        return flow {
            emit(ResponseClient.Loading)
            try {
                val apiResponse = api.getAllCategories(sessionStorage.get()?.sessionToken.orEmpty())
                val categories = apiResponse.map {
                    it.toCategory()
                }
                emit(ResponseClient.Success(categories))
            }
            catch (e: HttpException) {
                val errorResponse: DeliveryApiResponse = parseErrorResponse(e)
                emit(ResponseClient.Failure(Exception(errorResponse.message)))
            }
            catch (e: UnknownHostException) {
                emit(ResponseClient.Failure(Exception("No Internet Connection")))
                e.printStackTrace()
            } catch (e: IOException) {
                emit(ResponseClient.Failure(Exception("Network Error")))
                e.printStackTrace()
            } catch (e: Exception) {
                emit(ResponseClient.Failure(e))
                e.printStackTrace()
            }
        }
    }

}