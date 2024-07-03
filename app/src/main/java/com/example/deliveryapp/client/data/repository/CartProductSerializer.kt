package com.example.deliveryapp.client.data.repository

import androidx.datastore.core.Serializer
import com.example.deliveryapp.client.domain.model.CartShopping
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object CartProductSerializer: Serializer<CartShopping> {
    override val defaultValue: CartShopping
        get() = CartShopping()

    override suspend fun readFrom(input: InputStream): CartShopping {
        return try{
            Json.decodeFromString(
                deserializer = CartShopping.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: CartShopping, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = CartShopping.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}
