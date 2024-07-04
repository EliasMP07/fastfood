package com.example.deliveryapp.client.domain.useCases

data class ClientUseCases(
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val getAllProductByCategory: GetAllProductByCategory,
    val addProductCartUseCase: AddProductCartUseCase,
    val getCartShopping: GetCartShopping,
    val removeProductToCartUseCase: RemoveProductToCartUseCase,
    val updateAllCartUseCase: UpdateAllCartUseCase
)