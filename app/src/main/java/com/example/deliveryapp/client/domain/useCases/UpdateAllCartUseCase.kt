package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.repository.ClientRepository

class UpdateAllCartUseCase(
    private val repository: ClientRepository
){
    suspend operator fun invoke(cartShopping: CartShopping){
        repository.updateAllCart(cartShopping)
    }
}