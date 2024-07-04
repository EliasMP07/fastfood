package com.example.deliveryapp.client.data.repository

import com.example.deliveryapp.client.data.mapppers.toCategory
import com.example.deliveryapp.client.data.mapppers.toProduct
import com.example.deliveryapp.client.data.network.ClientApiServices
import com.example.deliveryapp.client.domain.mapper.toCartShopping
import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.CartShoppingSerializable
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.repository.CartRepository
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ClientRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val api: ClientApiServices,
    private val cartRepository: CartRepository
): ClientRepository{
    override suspend fun getProductByCategory(idCategory: String): Flow<Response<List<Product>>> {
        return ApiCallHelper.safeApiCall {
            val apiResponse = api.getProductsByCategory(token = sessionStorage.get()?.sessionToken.orEmpty(), idCategory = idCategory)
            val products = apiResponse.products.map {
                it.toProduct()
            }
            products
        }
    }

    override suspend fun getAllCategories(): Flow<Response<List<Category>>> {
        return ApiCallHelper.safeApiCall {
            val apiResponse = api.getAllCategories(sessionStorage.get()?.sessionToken.orEmpty())
            val categories = apiResponse.map {
                it.toCategory()
            }
            categories
        }
    }

    override suspend fun addCard(product: Product): Response<Unit> {
        return ApiCallHelper.safeApiCallNoFlow {
            cartRepository.addProductToCart(product)
        }
    }

    override suspend fun removeProductToCart(product: Product): Response<Unit> {
        return ApiCallHelper.safeApiCallNoFlow {
            cartRepository.removeOneProduct(product)
        }
    }

    override suspend fun getMyCard(): List<Product>{
        return cartRepository.getCartProduct()
    }

    override suspend fun updateAllCart(cartShopping: CartShopping) {
        cartRepository.updateAllCart(cartShopping)
    }


}
