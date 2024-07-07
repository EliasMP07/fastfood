package com.example.deliveryapp.client.presentation.productDetail

sealed interface DetailProductActions {
    data object OnAddProductClick: DetailProductActions
    data object OnRemoveProductClick: DetailProductActions
    data object OnAddCartClick: DetailProductActions
    data object OnReviewProductClick: DetailProductActions
    data class OnReviewProductChange(val rating: Double): DetailProductActions
    data object OnConfirmReviewProductClick: DetailProductActions
    data object OnBackClick: DetailProductActions
}