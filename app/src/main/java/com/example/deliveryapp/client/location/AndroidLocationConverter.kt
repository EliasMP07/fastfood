package com.example.deliveryapp.client.location

import android.content.Context
import android.location.Geocoder
import com.example.deliveryapp.client.domain.model.Location
import com.example.deliveryapp.client.domain.repository.LocationConverter
import com.example.deliveryapp.client.presentation.address.models.AddressInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AndroidLocationConverter(
    private val context: Context
) : LocationConverter {

    private val geocoder = Geocoder(context)

    override suspend fun getDirection(location: Location): Flow<AddressInfo> {
        return flow {
            val converter = geocoder.getFromLocationCompat(
                latitude = location.lat,
                longitude = location.long,
                maxResults = 1
            )
            if (converter.isNotEmpty()) {
                emit(
                    AddressInfo(
                        city = converter[0].locality?:"",
                        country = converter[0].countryName?:"",
                        address = converter[0].getAddressLine(0)?:""
                    )
                )
            }
        }
    }
}