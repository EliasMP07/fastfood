package com.example.deliveryapp.client.data.repository

import android.content.Context
import androidx.datastore.dataStore
import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.repository.CartRepository
import com.example.deliveryapp.client.domain.mapper.toProductUiModel
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class CartRepositoryImpl(
    private val context: Context
): CartRepository {

    private val Context.dataStore by dataStore("cartProducts.json", CartProductSerializer)

    override suspend fun addProductToCart(product: Product) {
        val products = getCartProduct().first()
        if (products.listProduct.none{it.id == product.id}){
            context.dataStore.updateData {
                it.copy(
                    listProduct = it.listProduct.toPersistentList().mutate{products ->
                        products.add(product.toProductUiModel())
                    }
                )
            }
        }
    }

    override suspend fun getCartProduct(): Flow<CartShopping> {
        return context.dataStore.data

    }

    override suspend fun clearCart() {
        context.dataStore.updateData {
            it.copy(
                listProduct = persistentListOf()
            )
        }
    }

}