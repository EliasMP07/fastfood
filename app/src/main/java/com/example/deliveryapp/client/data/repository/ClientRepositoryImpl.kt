package com.example.deliveryapp.client.data.repository

import com.example.deliveryapp.client.data.mapppers.toAddress
import com.example.deliveryapp.client.data.mapppers.toCategory
import com.example.deliveryapp.client.data.mapppers.toOrderClient
import com.example.deliveryapp.client.data.mapppers.toProduct
import com.example.deliveryapp.client.data.mapppers.toProductDto
import com.example.deliveryapp.client.data.network.ClientApiServices
import com.example.deliveryapp.client.data.network.dto.address.AddressRequest
import com.example.deliveryapp.client.data.network.dto.orders.OrderRequest
import com.example.deliveryapp.client.data.network.dto.rating.RatingRequest
import com.example.deliveryapp.client.domain.model.Address
import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.OrderClient
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.repository.CartRepository
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.data.remote.ApiCallHelper
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.repository.SessionStorage
import kotlinx.coroutines.flow.Flow

class ClientRepositoryImpl(
    private val sessionStorage: SessionStorage,
    private val api: ClientApiServices,
    private val cartRepository: CartRepository
) : ClientRepository {
    override suspend fun getProductByCategory(idCategory: String): Flow<Response<List<Product>>> {
        return ApiCallHelper.safeCallFlow {
            val apiResponse = api.getProductsByCategory(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                idCategory = idCategory
            )
            val products = apiResponse.data?.map {
                it.toProduct()
            }
            products?: listOf()
        }
    }

    override suspend fun getAllCategories(): Flow<Response<List<Category>>> {
        return ApiCallHelper.safeCallFlow {
            val apiResponse = api.getAllCategories(sessionStorage.get()?.sessionToken.orEmpty())
            val categories = apiResponse.map {
                it.toCategory()
            }
            categories
        }
    }

    override suspend fun addCard(product: Product): Response<Unit> {
        return ApiCallHelper.safeCall {
            cartRepository.addProductToCart(product)
        }
    }

    override suspend fun removeProductToCart(product: Product): Response<Unit> {
        return ApiCallHelper.safeCall {
            cartRepository.removeOneProduct(product)
        }
    }

    override suspend fun getMyCard(): List<Product> {
        return cartRepository.getCartProduct()
    }

    override suspend fun updateAllCart(cartShopping: CartShopping) {
        cartRepository.updateAllCart(cartShopping)
    }

    override suspend fun addRatingProduct(idProduct: String, rating: Double): Response<Unit> {
        return ApiCallHelper.safeCall {
            api.addRatingProduct(
                token = sessionStorage.get()?.sessionToken.orEmpty(), ratingRequest = RatingRequest(
                    productId = idProduct,
                    userId = sessionStorage.get()?.id.orEmpty(),
                    rating = rating
                )
            )
        }
    }

    override suspend fun getProductsPopular(): Flow<Response<List<Product>>> {
        return ApiCallHelper.safeCallFlow {
            val apiResponse = api.getProductsPopular(token = sessionStorage.get()?.sessionToken.orEmpty())
            val productsPopular = apiResponse.map {
                it.toProduct()
            }
            productsPopular
        }
    }

    override suspend fun createAddress(
        address: String,
        neighborhood: String,
        latitud: Double,
        longitud: Double
    ): Response<Unit> {
        return ApiCallHelper.safeCall {
            api.createAddress(token = sessionStorage.get()?.sessionToken.orEmpty(), addressRequest = AddressRequest(
                idUser = sessionStorage.get()?.id?:"",
                address = address,
                neighborhood = neighborhood,
                latitud = latitud,
                longitud = longitud
            ))
        }
    }

    override suspend fun getAddressByIdUser(): Response<List<Address>> {
        return ApiCallHelper.safeCall {
            val address =  api.getAddressByUser(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                idUser =  sessionStorage.get()?.id?:"",
            )
            address.map {
                it.toAddress()
            }
        }
    }

    override suspend fun createOrder(idAddress: String, status: String): Response<Unit> {
        return ApiCallHelper.safeCall {
            api.createOrder(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                orderRequest = OrderRequest(
                    idClient =  sessionStorage.get()?.id?:"",
                    idAddress = idAddress,
                    products = cartRepository.getCartProduct().map {
                        it.toProductDto()
                    },
                    status = status
                )
            )
        }
    }

    override suspend fun getStatusOrders(status: String): Flow<Response<List<OrderClient>>> {
        return ApiCallHelper.safeCallFlow {
            val apiResponse = api.getStatusMyOrder(
                token = sessionStorage.get()?.sessionToken.orEmpty(),
                status = status.uppercase(),
                idClient = sessionStorage.get()?.id?:"",
            )

            val orders = apiResponse.data?.map {
                it.toOrderClient()
            }
            orders?: listOf()
        }
    }


}
