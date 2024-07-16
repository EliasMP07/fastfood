package com.example.deliveryapp.restaurant.domain.model


data class DeliveryAvailable(
    val email: String,
    val id: String,
    val image: String,
    val lastname: String,
    val name: String,
    val password: String,
    val phone: String,
    val sessionToken: String
){
    override fun toString(): String {
        return name
    }
}
