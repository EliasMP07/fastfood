package com.example.deliveryapp.client.domain.repository

import com.example.deliveryapp.client.domain.model.Address
import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.OrderClient
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.flow.Flow

interface ClientRepository {
    suspend fun getProductByCategory(idCategory: String): Flow<Response<List<Product>>>
    suspend fun getAllCategories(): Flow<Response<List<Category>>>
    suspend fun addCard(product: Product): Response<Unit>
    suspend fun removeProductToCart(product: Product): Response<Unit>
    suspend fun getMyCard(): List<Product>
    suspend fun updateAllCart(cartShopping: CartShopping)
    suspend fun addRatingProduct(idProduct: String, rating: Double): Response<Unit>
    suspend fun getProductsPopular(): Flow<Response<List<Product>>>
    suspend fun createAddress(address: String, neighborhood: String, latitud: Double, longitud: Double): Response<Unit>
    suspend fun getAddressByIdUser(): Response<List<Address>>
    suspend fun createOrder(idAddress: String, status: String): Response<Unit>
    suspend fun getStatusOrders(status: String): Flow<Response<List<OrderClient>>>
}