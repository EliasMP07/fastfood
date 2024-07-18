package com.example.deliveryapp.client.presentation.address.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.useCases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientMyAddressViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases,
    private val userUseCase: UserUseCase
) : ViewModel() {


    //Variable que contiene los estados de la Ui
    private val _state = MutableStateFlow(ClientMyAddressState())
    val state: StateFlow<ClientMyAddressState> get() = _state.asStateFlow()

    init {
        getMyAddress()
    }

    fun getMyAddress() {
        viewModelScope.launch {
            val result = clientUseCases.getAddressByUserId()
            val favoriteAddress = userUseCase.getAddressFavoriteUseCase()?:""
            _state.update {
                when (result) {
                    is Response.Failure -> {
                        it.copy(
                            isError = true,
                            isLoading = false
                        )
                    }
                    Response.Loading -> {
                        it.copy(
                            isError = false,
                            isLoading = true
                        )
                    }
                    is Response.Success -> {
                        it.copy(
                            myAddress = result.data,
                            isError = false,
                            isLoading = false,
                            selectedAddressId = favoriteAddress
                        )
                    }
                }
            }
        }
    }

    fun selectAddress(addressId: String) {
        viewModelScope.launch {
            userUseCase.addFavoriteAddressUseCase(addressId)
        }
        _state.update {currentState ->
            currentState.copy(
                selectedAddressId = addressId
            )
        }
    }

    fun onAction(action: ClientMyAddressAction) {
        when (action) {
            is ClientMyAddressAction.OnSelectedAddress -> {
                selectAddress(action.addressId)
            }
            else -> Unit
        }
    }

}
