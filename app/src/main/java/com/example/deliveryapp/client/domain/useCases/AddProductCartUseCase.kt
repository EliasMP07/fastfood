package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddProductCartUseCase(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(product: Product): Response<Unit>{
        return withContext(Dispatchers.IO){
            repository.addCard(product)
        }
    }
}