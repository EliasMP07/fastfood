package com.example.deliveryapp.client.di

import android.content.Context
import com.example.deliveryapp.client.data.network.ClientApiServices
import com.example.deliveryapp.client.data.repository.ClientRepositoryImpl
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.client.domain.useCases.GetAllCategoriesUseCase
import com.example.deliveryapp.client.domain.useCases.GetAllProductByCategory
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientModule {

    @Provides
    @Singleton
    fun provideClientRepository(
        sessionStorage: SessionStorage,
        api: ClientApiServices
    ): ClientRepository{
        return ClientRepositoryImpl(
            sessionStorage = sessionStorage,
            api = api
        )
    }

    @Provides
    @Singleton
    fun provideClientUseCases(
        repository: ClientRepository
    ): ClientUseCases{
        return ClientUseCases(
            getAllCategoriesUseCase = GetAllCategoriesUseCase(repository),
            getAllProductByCategory = GetAllProductByCategory(repository)
        )
    }
}