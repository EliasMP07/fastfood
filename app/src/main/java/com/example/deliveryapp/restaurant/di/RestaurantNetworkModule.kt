package com.example.deliveryapp.restaurant.di

import com.example.deliveryapp.auth.data.remote.AuthApiService
import com.example.deliveryapp.restaurant.data.remote.CategoryApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantNetworkModule {
    @Provides
    @Singleton
    fun provideCategoryApiService(): CategoryApiService{
        return Retrofit.Builder()
            .baseUrl(AuthApiService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}