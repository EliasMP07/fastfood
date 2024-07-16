package com.example.deliveryapp.restaurant.domain.usecases.category

data class RestaurantUseCases(
    val createCategoryUseCase: CreateCategoryUseCase,
    val getAllCategoriesUseCase: GetAllCategoriesUseCase,
    val createProductUseCase: CreateProductUseCase,
    val getAllOrdersUseCase: GetAllOrdersUseCase,
    val getDeliveriesAvailableUseCase: GetDeliveriesAvailableUseCase,
    val assignDeliveryUseCase: AssignDeliveryUseCase
)