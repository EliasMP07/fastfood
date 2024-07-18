package com.example.deliveryapp.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.example.deliveryapp.core.domain.repository.LocationConverter
import com.example.deliveryapp.core.domain.repository.LocationObserver
import com.example.deliveryapp.core.location.AndroidLocationConverter
import com.example.deliveryapp.core.location.AndroidLocationObserver
import com.example.deliveryapp.core.user.data.network.UserApiService
import com.example.deliveryapp.core.user.data.repository.UserRepositoryImp
import com.example.deliveryapp.core.user.data.repository.UserSessionStorage
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.core.user.domain.repository.UserRepository
import com.example.deliveryapp.core.user.domain.useCases.AddFavoriteAddressUseCase
import com.example.deliveryapp.core.user.domain.useCases.GetAddressFavoriteUseCase
import com.example.deliveryapp.core.user.domain.useCases.UpdateProfileUseCase
import com.example.deliveryapp.core.user.domain.useCases.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

private const val USER_PREFERENCES = "user_preferences"

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideUserUseCase(
        repository: UserRepository
    ): UserUseCase{
        return UserUseCase(
            updateProfileUseCase = UpdateProfileUseCase(repository = repository),
            getAddressFavoriteUseCase = GetAddressFavoriteUseCase(repository),
            addFavoriteAddressUseCase = AddFavoriteAddressUseCase(repository)
        )
    }

    @Provides
    @Singleton
    fun provideLocationConverter(
        @ApplicationContext context: Context
    ): LocationConverter {
        return AndroidLocationConverter(context)
    }

    @Provides
    @Singleton
    fun provideLocationObserver(
        @ApplicationContext context: Context,
    ): LocationObserver {
        return AndroidLocationObserver(context)
    }

    @Singleton
    @Provides
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(context, USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { context.preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }

    @Singleton
    @Provides
    fun provideSessionStorage(userPreferences: DataStore<Preferences>): SessionStorage {
        return UserSessionStorage(userPreferences)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        sessionStorage: SessionStorage,
        api: UserApiService,
        userPreferences: DataStore<Preferences>
    ): UserRepository {
        return UserRepositoryImp(api = api, sessionStorage = sessionStorage, userPreferences= userPreferences)
    }

}