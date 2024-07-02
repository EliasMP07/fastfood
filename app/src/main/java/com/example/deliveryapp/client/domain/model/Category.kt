package com.example.deliveryapp.client.domain.model

data class Category(
    val id: String = "",
    val name: String = "",
    val image: String = ""
){
    override fun toString(): String {
        return name
    }
}
