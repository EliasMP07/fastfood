package com.example.deliveryapp.core.presentation.ui.ex

import android.view.View

fun View.clearFocusFromAllFields(vararg views: View) {
    views.forEach { it.clearFocus() }
}
