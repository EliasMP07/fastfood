package com.example.deliveryapp.core.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import com.example.deliveryapp.R
import java.time.LocalTime

@SuppressLint("NewApi")
fun getMealTime(context: Context): String {
    val now = LocalTime.now()
    return when {
        now.isBefore(LocalTime.of(10, 0)) -> context.getString(R.string.breakfast)
        now.isBefore(LocalTime.of(14, 0)) -> context.getString(R.string.lunch)
        now.isBefore(LocalTime.of(18, 0)) -> context.getString(R.string.snack)
        else -> context.getString(R.string.dinner)
    }
}