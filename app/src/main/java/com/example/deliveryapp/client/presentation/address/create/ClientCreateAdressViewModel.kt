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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientCreateAddressViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases
) : ViewModel(), MVVMContract<ClienteCreateAddressState, ClientCreateAddressAction> by MVVMDelegate(
    ClienteCreateAddressState()
) {

    private val eventChannel = Channel<ClientCreateAddressEvent>()
    val events = eventChannel.receiveAsFlow()

    override fun onAction(action: ClientCreateAddressAction) {
        when (action) {
            is ClientCreateAddressAction.OnReferenceMapChange -> {
                updateUi {
                    copy(
                        addressInfo = action.addressInfo
                    )
                }
            }

            is ClientCreateAddressAction.OnAddressChange -> {
                updateUi {
                    copy(
                        address = action.address
                    )
                }
            }

            is ClientCreateAddressAction.OnNeighborhoodChange -> {
                updateUi {
                    copy(
                        neighborhoodChange = action.neighborhood
                    )
                }
            }

            ClientCreateAddressAction.OnCreateAddressClick -> createAddress()
            else -> Unit
        }
    }

    private fun createAddress() {
        val currentState = uiState.value
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

interface MVVMContract<State, Action> {

    val uiState: StateFlow<State>

    fun onAction(action: Action)

    fun setState(newState: State)

    fun updateUi(update: State.() -> State)

}


class MVVMDelegate<State, Action> internal constructor(
    initialUiState: State
) : MVVMContract<State, Action> {

    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<State> get() = _uiState

    override fun updateUi(update: State.() -> State) {
        _uiState.value = _uiState.value.update()
    }

    override fun setState(newState: State) {
        _uiState.value = newState
    }

    override fun onAction(action: Action) {
    }
}
