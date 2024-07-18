package com.example.deliveryapp.client.di

import android.content.Context
import com.example.deliveryapp.client.data.network.ClientApiServices
import com.example.deliveryapp.client.data.repository.CartRepositoryImpl
import com.example.deliveryapp.client.data.repository.ClientRepositoryImpl
import com.example.deliveryapp.client.domain.repository.CartRepository
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.client.domain.useCases.AddProductCartUseCase
import com.example.deliveryapp.client.domain.useCases.AddRatingProductUseCase
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.client.domain.useCases.CreateAddressUseCase
import com.example.deliveryapp.client.domain.useCases.GetAddressByUserId
import com.example.deliveryapp.client.domain.useCases.GetAllCategoriesUseCase
import com.example.deliveryapp.client.domain.useCases.GetAllProductByCategory
import com.example.deliveryapp.client.domain.useCases.GetCartShopping
import com.example.deliveryapp.client.domain.useCases.GetProductsPopularUseCase
import com.example.deliveryapp.client.domain.useCases.GetStatusOrdersUseCase
import com.example.deliveryapp.client.domain.useCases.RemoveProductToCartUseCase
import com.example.deliveryapp.client.domain.useCases.UpdateAllCartUseCase
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
        api: ClientApiServices,
        cartRepository: CartRepository
    ): ClientRepository{
        return ClientRepositoryImpl(
            sessionStorage = sessionStorage,
            api = api,
            cartRepository = cartRepository
        )
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        @ApplicationContext context: Context
    ): CartRepository{
        return CartRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideClientUseCases(
        repository: ClientRepository
    ): ClientUseCases{
        return ClientUseCases(
            getAllCategoriesUseCase = GetAllCategoriesUseCase(repository),
            getAllProductByCategory = GetAllProductByCategory(repository),
            addProductCartUseCase = AddProductCartUseCase(repository),
            getCartShopping = GetCartShopping(repository),
            removeProductToCartUseCase = RemoveProductToCartUseCase(repository),
            updateAllCartUseCase = UpdateAllCartUseCase(repository),
            addRatingProductUseCase = AddRatingProductUseCase(repository),
            getProductsPopularUseCase = GetProductsPopularUseCase(repository),
            createAddressUseCase = CreateAddressUseCase(repository),
            getAddressByUserId = GetAddressByUserId(repository),
            getStatusOrdersUseCase = GetStatusOrdersUseCase(repository)
        )
    }
}