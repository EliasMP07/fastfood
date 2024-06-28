package com.example.deliveryapp.auth.presentation.register

//Interfaz que contiene las acciones en el registro
sealed interface RegisterAction {

    data class OnEmailChange(val email: String): RegisterAction
    data class OnPasswordChange(val password: String): RegisterAction
    data class OnPhoneNumberChange(val phoneNumber: String): RegisterAction
    data class OnNameChange(val name: String): RegisterAction
    data class OnLastNameChange(val lastName: String): RegisterAction
    data object OnRegisterClick: RegisterAction
    data object OnLoginClick: RegisterAction
    data object OnTakePhotoSelected: RegisterAction
    data class OnImageGalleryChange(val image: String): RegisterAction
    data class OnImageCamaraChange(val image: String): RegisterAction

}