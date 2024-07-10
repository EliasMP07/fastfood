package com.example.deliveryapp.core.user.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.data.mapper.toUser
import com.example.deliveryapp.core.user.data.mapper.toUserRequestDto
import com.example.deliveryapp.core.user.data.network.UserApiService
import com.example.deliveryapp.core.user.data.utils.PreferencesKeys.ADDRESS_FAVORITE
import com.example.deliveryapp.core.user.domain.model.User
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.core.user.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext

class UserRepositoryImp(
    private val api: UserApiService,
    private val sessionStorage: SessionStorage,
    private val userPreferences: DataStore<Preferences>
): UserRepository {

    override suspend fun update(user: User): Response<User> {
        return ApiCallHelper.safeCall {
            val response = api.updateProfile(userRequestDto = user.toUserRequestDto(), token = user.sessionToken)
            if (response.success && response.data != null){
                sessionStorage.set(response.data.toUser())
            }
            response.data!!.toUser()
        }
    }

    override suspend fun addressFavorite(idDirection: String?) {
        withContext(Dispatchers.IO){
            try {
                userPreferences.edit { preferences ->
                    if (idDirection == null){
                        preferences[ADDRESS_FAVORITE] = ""
                    }else{
                        preferences[ADDRESS_FAVORITE] = idDirection
                    }
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getAddressFavorite(): String? {
        return withContext(Dispatchers.IO) {
            try {
                val preferences = userPreferences.data.firstOrNull() ?: return@withContext null
                val addressFavorite = preferences[ADDRESS_FAVORITE] ?: return@withContext null
                addressFavorite
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}