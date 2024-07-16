package com.example.deliveryapp.delivery.di

import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.delivery.data.network.DeliveryApiService
import com.example.deliveryapp.delivery.data.repository.DeliveryRepositoryImpl
import com.example.deliveryapp.delivery.domain.repository.DeliveryRepository
import com.example.deliveryapp.delivery.domain.usecases.DeliveryUseCases
import com.example.deliveryapp.delivery.domain.usecases.GetOrderDeliveryStatusUseCase
import com.example.deliveryapp.delivery.domain.usecases.InitDeliveryOrderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeliveryModule {

    @Singleton
    @Provides
    fun provideDeliveryRepository(
        api: DeliveryApiService,
        sessionStorage: SessionStorage
    ): DeliveryRepository{
        return DeliveryRepositoryImpl(api = api, sessionStorage = sessionStorage)
    }

    @Singleton
    @Provides
    fun provideDeliveryUseCases(
        repository: DeliveryRepository
    ): DeliveryUseCases{
        return DeliveryUseCases(
            getOrderDeliveryStatusUseCase = GetOrderDeliveryStatusUseCase(repository),
            initDeliveryOrderUseCase = InitDeliveryOrderUseCase(repository)
        )
    }
}