package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.flow.Flow

class GetProductsPopularUseCase(
    private val repository: ClientRepository
){
    suspend operator fun invoke(): Flow<Response<List<Product>>>{
        return repository.getProductsPopular()
    }
}
