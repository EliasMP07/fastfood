package com.example.deliveryapp.client.presentation.products

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.Product

data class ClientProductListState(
    val listProducts: List<Product> = listOf(),
    val category: Category = Category(),
    val isLoading: Boolean = false
)
