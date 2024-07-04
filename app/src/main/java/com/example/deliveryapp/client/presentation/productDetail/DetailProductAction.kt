package com.example.deliveryapp.client.presentation.productDetail

sealed interface DetailProductActions {
    data object OnAddProductClick: DetailProductActions
    data object OnRemoveProductClick: DetailProductActions
    data object OnAddCartClick: DetailProductActions
    data object OnReviewProductClick: DetailProductActions
}