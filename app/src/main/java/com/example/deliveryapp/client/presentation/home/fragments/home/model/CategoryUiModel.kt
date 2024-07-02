package com.example.deliveryapp.client.presentation.home.fragments.home.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryUiModel(
    val id: String = "",
    val name: String = "",
    val image: String = ""
)
