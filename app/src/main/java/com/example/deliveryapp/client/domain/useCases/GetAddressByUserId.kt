package com.example.deliveryapp.client.domain.useCases

import com.example.deliveryapp.client.domain.model.Address
import com.example.deliveryapp.client.domain.repository.ClientRepository
import com.example.deliveryapp.core.domain.model.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetAddressByUserId(
    private val repository: ClientRepository
) {
    suspend operator fun invoke(): Response<List<Address>>{
        return withContext(Dispatchers.IO){
            repository.getAddressByIdUser()
        }
    }
}