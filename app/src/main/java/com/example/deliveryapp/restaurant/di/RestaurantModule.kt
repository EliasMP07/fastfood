package com.example.deliveryapp.restaurant.di

import android.content.Context
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.restaurant.data.remote.RestaurantApiService
import com.example.deliveryapp.restaurant.data.repository.RestaurantRepositoryImpl
import com.example.deliveryapp.restaurant.domain.repository.RestaurantRepository
import com.example.deliveryapp.restaurant.domain.usecases.category.RestaurantUseCases
import com.example.deliveryapp.restaurant.domain.usecases.category.CreateCategoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RestaurantModule {

    @Singleton
    @Provides
    fun provideCategoryRepository(
        sessionStorage: SessionStorage,
        restaurantApiService: RestaurantApiService,
        @ApplicationContext context: Context

    ): RestaurantRepository {
        return RestaurantRepositoryImpl(
            api = restaurantApiService,
            context = context,
            sessionStorage = sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideCategoryUseCases(
        repository: RestaurantRepository
    ): RestaurantUseCases {
        return RestaurantUseCases(
            createCategoryUseCase = CreateCategoryUseCase(repository)
        )
    }
}