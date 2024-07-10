package com.example.deliveryapp.core.presentation.ui

import android.app.Activity
import android.content.Intent

fun Activity.startActivityWithFinish(
    intent: Intent
){
    startActivity(intent.apply {
        addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    })
    finish()
}