package com.example.deliveryapp.client.presentation.address.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.model.Location
import com.example.deliveryapp.client.domain.repository.LocationConverter
import com.example.deliveryapp.client.domain.repository.LocationObserver
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapGoogleViewModel @Inject constructor(
    private val locationObserver: LocationObserver,
    private val locationConverter: LocationConverter
) : ViewModel() {

    private val _state = MutableStateFlow(MapGoogleState())
    val state: StateFlow<MapGoogleState> get() = _state.asStateFlow()

    init {
        observeLocation()
    }

    private fun observeLocation() {
        viewModelScope.launch {
            locationObserver.observeLocation(1000L).collectLatest { location ->
                _state.update {
                    it.copy(
                        currentLocation = location.location,
                        shouldFollowLocation = it.shouldFollowLocation
                    )
                }
            }
        }
    }

    fun onAction(action: MapGoogleAction) {
        when (action) {
            is MapGoogleAction.OnDirectionChange -> {
                updateCameraPosition(action.camaraPosition)
                getLocationDirection(action.camaraPosition)
            }
        }
    }

    private fun updateCameraPosition(cameraPosition: Location) {
        _state.update {
            it.copy(
                cameraPosition = cameraPosition,
                shouldFollowLocation = false
            )
        }
    }

    private fun getLocationDirection(location: Location) {
        viewModelScope.launch {
            locationConverter.getDirection(location).collectLatest {
                _state.update { currentState ->
                    currentState.copy(
                        addressInfo = it.copy(
                            lat = location.lat,
                            log = location.long
                        )
                    )
                }
            }
        }
    }

    fun insertPosition(latLng: LatLng) {
        if (_state.value.shouldFollowLocation) {
            getLocationDirection(Location(
                long = latLng.longitude,
                lat = latLng.latitude
            ))
        }
    }
}
