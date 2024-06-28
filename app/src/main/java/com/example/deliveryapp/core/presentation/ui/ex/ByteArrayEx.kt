package com.example.deliveryapp.core.presentation.ui.ex

import android.util.Base64

fun ByteArray.toBase64(): String {
    return Base64.encodeToString(this, Base64.NO_WRAP)
}