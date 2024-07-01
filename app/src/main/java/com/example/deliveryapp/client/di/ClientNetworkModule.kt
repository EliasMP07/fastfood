package com.example.deliveryapp.client.di

import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.client.data.network.ClientApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ClientNetworkModule {

    @Provides
    @Singleton
    fun provideClientApiService(retrofit: Retrofit): ClientApiServices{
        return retrofit.create(ClientApiServices::class.java)
    }
}