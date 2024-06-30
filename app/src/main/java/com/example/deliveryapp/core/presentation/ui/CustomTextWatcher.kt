package com.example.deliveryapp.core.presentation.ui

import android.text.Editable
import android.text.TextWatcher

class CustomTextWatcher(
    private val onTextChanged: (String) -> Unit
) : TextWatcher {

    private var previousText: String? = null

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        previousText = s?.toString()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // No action needed here
    }

    override fun afterTextChanged(editable: Editable?) {
        val newText = editable?.toString()
        if (newText != previousText) {
            onTextChanged(newText ?: "")
        }
    }
}
