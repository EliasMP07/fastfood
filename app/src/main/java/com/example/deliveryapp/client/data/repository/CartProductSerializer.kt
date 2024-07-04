package com.example.deliveryapp.client.data.repository

import androidx.datastore.core.Serializer
import com.example.deliveryapp.client.domain.model.CartShoppingSerializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object CartProductSerializer: Serializer<CartShoppingSerializable> {
    override val defaultValue: CartShoppingSerializable
        get() = CartShoppingSerializable()

    override suspend fun readFrom(input: InputStream): CartShoppingSerializable {
        return try{
            Json.decodeFromString(
                deserializer = CartShoppingSerializable.serializer(),
                string = input.readBytes().decodeToString()
            )
        }catch (e: SerializationException){
            e.printStackTrace()
            defaultValue
        }
    }

    override suspend fun writeTo(t: CartShoppingSerializable, output: OutputStream) {
        output.write(
            Json.encodeToString(
                serializer = CartShoppingSerializable.serializer(),
                value = t
            ).encodeToByteArray()
        )
    }

}
