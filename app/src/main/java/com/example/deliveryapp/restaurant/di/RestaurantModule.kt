package com.example.deliveryapp.restaurant.di

import android.content.Context
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import com.example.deliveryapp.restaurant.data.remote.CategoryApiService
import com.example.deliveryapp.restaurant.data.repository.CategoryRepositoryImpl
import com.example.deliveryapp.restaurant.domain.repository.CategoryRepository
import com.example.deliveryapp.restaurant.domain.usecases.category.CategoryUseCases
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
        categoryApiService: CategoryApiService,
        @ApplicationContext context: Context

    ): CategoryRepository {
        return CategoryRepositoryImpl(
            api = categoryApiService,
            context = context,
            sessionStorage = sessionStorage
        )
    }

    @Singleton
    @Provides
    fun provideCategoryUseCases(
        repository: CategoryRepository
    ): CategoryUseCases {
        return CategoryUseCases(
            createCategoryUseCase = CreateCategoryUseCase(repository)
        )
    }
}