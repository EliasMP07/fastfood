package com.example.deliveryapp.delivery.di

import com.example.deliveryapp.delivery.data.network.DeliveryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeliveryNetworkModule {

    @Singleton
    @Provides
    fun provideDeliveryApiService(retrofit: Retrofit): DeliveryApiService{
        return retrofit.create(DeliveryApiService::class.java)
    }
}