package com.example.deliveryapp.client.presentation.address.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.client.presentation.address.create.MVVMContract
import com.example.deliveryapp.client.presentation.address.create.MVVMDelegate
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.user.domain.useCases.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientMyAddressViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases,
    private val userUseCase: UserUseCase
) : ViewModel(), MVVMContract<ClientMyAddressState, ClientMyAddressAction> by MVVMDelegate(
    ClientMyAddressState()
) {

    init {
        getMyAddress()
    }

    fun getMyAddress() {
        viewModelScope.launch {
            val result = clientUseCases.getAddressByUserId()
            val favoriteAddress = userUseCase.getAddressFavoriteUseCase()?:""
            updateUi {
                when (result) {
                    is Response.Failure -> {
                        copy(
                            isError = true,
                            isLoading = false
                        )
                    }
                    Response.Loading -> {
                        copy(
                            isError = false,
                            isLoading = true
                        )
                    }
                    is Response.Success -> {
                        copy(
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
        updateUi {
            copy(
                selectedAddressId = addressId
            )
        }
    }

    override fun onAction(action: ClientMyAddressAction) {
        when (action) {
            is ClientMyAddressAction.OnSelectedAddress -> {
                selectAddress(action.addressId)
            }
            else -> Unit
        }
    }

}
