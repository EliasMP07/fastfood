package com.example.deliveryapp.core.presentation.ui

import java.time.LocalTime

fun getMealTime(): String {
    val now = LocalTime.now()
    return when {
        now.isBefore(LocalTime.of(10, 0)) -> "Desayunar"
        now.isBefore(LocalTime.of(14, 0)) -> "Comer"
        now.isBefore(LocalTime.of(18, 0)) -> "Meriendar"
        else -> "Cenar"
    }
}