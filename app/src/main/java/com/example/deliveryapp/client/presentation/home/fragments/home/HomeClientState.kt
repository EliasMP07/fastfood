package com.example.deliveryapp.client.presentation.home.fragments.home

import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.model.Product
import com.example.deliveryapp.core.user.domain.model.User

data class HomeClientState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val listCategories: List<Category> = listOf(),
    val user: User = User(),
    val listProductsPopular: List<Product> = listOf()
)
