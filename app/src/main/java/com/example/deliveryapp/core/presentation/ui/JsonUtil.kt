package com.example.deliveryapp.core.presentation.ui

import com.google.gson.Gson

object JsonUtil {
    private val gson = Gson()

    fun <T> serialize(obj: T, clazz: Class<T>): String {
        return gson.toJson(obj, clazz)
    }

    fun <T> deserialize(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }
}