package com.example.deliveryapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DeliveryApp : Application(){

    companion object{
        private lateinit var instancia: DeliveryApp

        fun getContext(): Context{
            return instancia.applicationContext
        }
    }
    init {
        instancia = this
    }
}