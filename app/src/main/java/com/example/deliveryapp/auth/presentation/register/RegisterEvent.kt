package com.example.deliveryapp.auth.presentation.register

import com.example.deliveryapp.core.presentation.ui.UiText

//Interfaz que contiene los eventos de Registro
sealed interface RegisterEvent {

    data object Success: RegisterEvent
    data class Error(val error: UiText): RegisterEvent

}