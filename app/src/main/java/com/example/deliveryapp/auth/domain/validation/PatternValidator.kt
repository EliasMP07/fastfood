package com.example.deliveryapp.auth.domain.validation

interface PatternValidator {

    fun matches(value: String): Boolean

}