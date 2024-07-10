package com.example.deliveryapp.client.data.repository

import android.content.Context
import androidx.datastore.dataStore
import com.example.deliveryapp.client.domain.mapper.toProduct
import com.example.deliveryapp.client.domain.mapper.toProductSerializable
import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.CartShoppingSerializable
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.repository.CartRepository
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
        val products = getCartProductFlow().first()

        // Verificar si el producto ya existe
        val productIndex = products.listProduct.indexOfFirst { it.id == product.id }

        if (productIndex == -1) {
            // Si el producto no existe, agregarlo
            context.dataStore.updateData {
                it.copy(
                    listProduct = it.listProduct.toPersistentList().mutate { products ->
                        products.add(product.toProductSerializable())
                    }
                )
            }
        } else {
            // Si el producto ya existe, reemplazarlo
            context.dataStore.updateData {
                it.copy(
                    listProduct = it.listProduct.toPersistentList().mutate { products ->
                        products[productIndex] = product.toProductSerializable()
                    }
                )
            }
        }
    }

    // Método privado para obtener el Flow
    private fun getCartProductFlow(): Flow<CartShoppingSerializable> {
        return context.dataStore.data
    }

    override suspend fun getCartProduct(): List<Product> {
        return getCartProductFlow().first().listProduct.map { it.toProduct() }
    }

    override suspend fun removeOneProduct(product: Product) {
        context.dataStore.updateData {
            it.copy(
                listProduct = it.listProduct.toPersistentList().mutate {
                    it.remove(product.toProductSerializable())
                }
            )
        }
    }

    override suspend fun updateAllCart(cartShopping: CartShopping) {
        context.dataStore.updateData {
            it.copy(
                listProduct = cartShopping.listProducts.map {product ->
                    product.toProductSerializable()
                }
            )
        }
    }

    override suspend fun clearCart() {
        context.dataStore.updateData {
            it.copy(
                listProduct = persistentListOf()
            )
        }
    }

}