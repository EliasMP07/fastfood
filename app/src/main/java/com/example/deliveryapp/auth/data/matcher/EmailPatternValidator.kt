package com.example.deliveryapp.auth.data.matcher

import android.util.Patterns
import com.example.deliveryapp.auth.domain.validation.PatternValidator

class EmailPatternValidator (): PatternValidator {
    override fun matches(value: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(value).matches()
    }
}