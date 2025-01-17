package com.example.deliveryapp.auth.di

import com.example.deliveryapp.auth.data.remote.AuthApiService
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
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuthRoute(retrofit: Retrofit): AuthApiService{
        return retrofit.create(AuthApiService::class.java)
    }
}