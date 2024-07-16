package com.example.deliveryapp.delivery.domain.usecases

data class DeliveryUseCases(
    val getOrderDeliveryStatusUseCase: GetOrderDeliveryStatusUseCase,
    val initDeliveryOrderUseCase: InitDeliveryOrderUseCase
)
