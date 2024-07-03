package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.client.domain.repository.ClientRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class GetCartShopping(
    private val repository: ClientRepository
){
    suspend operator fun invoke(): Flow<CartShopping> {
        return withContext(Dispatchers.IO){
            repository.getMyCard()
        }
    }
}