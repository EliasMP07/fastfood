package com.example.deliveryapp.auth.presentation.register

//Interfaz que contiene los eventos de Registro
sealed interface RegisterEvent {

    data object Success: RegisterEvent
    data class Error(val message: String): RegisterEvent

}