package com.example.deliveryapp.client.presentation.address.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientCreateAddressViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases
) : ViewModel(){

    //Variable que contiene los estados de la Ui
    private val _state = MutableStateFlow(ClienteCreateAddressState())
    val state: StateFlow<ClienteCreateAddressState> get() = _state.asStateFlow()

    private val eventChannel = Channel<ClientCreateAddressEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: ClientCreateAddressAction) {
        when (action) {
            is ClientCreateAddressAction.OnReferenceMapChange -> {
                _state.update {currentState ->
                    currentState.copy(
                        addressInfo = action.addressInfo
                    )
                }
            }

            is ClientCreateAddressAction.OnAddressChange -> {
                _state.update {currentState ->
                    currentState.copy(
                        address = action.address
                    )
                }
            }

            is ClientCreateAddressAction.OnNeighborhoodChange -> {
                _state.update {currentState ->
                    currentState.copy(
                        neighborhoodChange = action.neighborhood
                    )
                }
            }

            ClientCreateAddressAction.OnCreateAddressClick -> createAddress()
            else -> Unit
        }
    }

    private fun createAddress() {
        val currentState = state.value
        viewModelScope.launch {
            val result = clientUseCases.createAddressUseCase(
                address = currentState.address,
                neighborhood = currentState.neighborhoodChange,
                latitud = currentState.addressInfo.lat,
                longitud = currentState.addressInfo.log
            )
            when (result) {
                is Response.Failure -> {
                    eventChannel.send(ClientCreateAddressEvent.OnError(result.error))
                }

                is Response.Success -> {
                    eventChannel.send(ClientCreateAddressEvent.OnSuccess(UiText.StringResource(R.string.on_success_create_address)))
                }

                else -> Unit
            }
        }
    }

}