package com.example.deliveryapp.client.presentation.updateProfile

sealed interface UpdateProfileAction {
    data object OnBackClick: UpdateProfileAction
    data class OnNameChange(val name: String): UpdateProfileAction
    data class OnLastNameChange(val lastName: String): UpdateProfileAction
    data class OnPhoneChange(val phone: String): UpdateProfileAction
    data object OnImageProfileSelectedClick: UpdateProfileAction
    data object OnUpdateProfileClick: UpdateProfileAction
    data class OnImageGalleryChange(val image: String): UpdateProfileAction
    data class OnImageCamaraChange(val image: String): UpdateProfileAction
}