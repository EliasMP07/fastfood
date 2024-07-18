@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.deliveryapp.core.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.content.getSystemService
import com.example.deliveryapp.client.presentation.address.map.toLocationWithAltitude
import com.example.deliveryapp.core.domain.model.Location
import com.example.deliveryapp.core.domain.model.LocationWithAltitude
import com.example.deliveryapp.core.domain.repository.LocationObserver
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidLocationObserver(
    private val context: Context,
) : LocationObserver {

    private val client = LocationServices.getFusedLocationProviderClient(context)

    override fun observeLocation(interval: Long): Flow<LocationWithAltitude> {
        return callbackFlow {
            val locationManager = context.getSystemService<LocationManager>()!!
            var isGpsEnabled = false
            var isNetworkEnabled = false
            while (!isGpsEnabled && !isNetworkEnabled) {
                isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

                if (!isGpsEnabled && !isNetworkEnabled) {
                    delay(3000L)
                }
            }

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                close()
            } else {
                client.lastLocation.addOnSuccessListener {

                    it?.let { location ->
                        trySend(location.toLocationWithAltitude())
                    }
                }

                val request = LocationRequest.Builder(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    interval
                )
                    .build()

                val locationCallback = object : LocationCallback() {
                    override fun onLocationResult(result: LocationResult) {
                        super.onLocationResult(result)
                        result.locations.lastOrNull()?.let { location ->
                            trySend(location.toLocationWithAltitude())
                        }
                    }
                }

                client.requestLocationUpdates(
                    request,
                    locationCallback,
                    Looper.getMainLooper()
                )

                awaitClose {
                    client.removeLocationUpdates(locationCallback)
                }
            }
        }
    }

    override suspend fun getLocation(interval: Long): Location {
        val locationManager = context.getSystemService<LocationManager>()!!
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGpsEnabled || !isNetworkEnabled){
            return Location(lat = 0.0, long = 0.0)
        }

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return Location(lat = 0.0, long = 0.0)
        } else {
            return suspendCancellableCoroutine {locationCoroutine ->
                client.lastLocation.apply {
                    if (isComplete){
                        if (isSuccessful){
                            locationCoroutine.resume(Location(lat = result.latitude, long = result.longitude)){}
                        }else{
                            locationCoroutine.resume(Location(0.0, 0.0))
                        }
                        return@suspendCancellableCoroutine
                    }
                    addOnSuccessListener {
                        locationCoroutine.resume(Location(lat = it.latitude, long = it.longitude)){}
                    }
                    addOnFailureListener {
                        locationCoroutine.resume(Location(0.0, 0.0))
                    }
                    addOnCanceledListener {
                        locationCoroutine.resume(Location(0.0, 0.0))
                    }
                }
            }
        }
    }

}