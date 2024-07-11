package com.example.deliveryapp.client.presentation.home.fragments.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.client.presentation.address.create.MVVMContract
import com.example.deliveryapp.client.presentation.address.create.MVVMDelegate
import com.example.deliveryapp.core.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientOrderStatusViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases
): ViewModel(), MVVMContract<ClientOrderStatusState, ClientOrderStatusAction> by MVVMDelegate(
    ClientOrderStatusState()
){

    fun getMyOrders(status: String){
        viewModelScope.launch {
            clientUseCases.getStatusOrdersUseCase(status = status).collect {result ->
                updateUi {
                    when(result){
                        is Response.Failure -> {
                            copy(
                                isLoading = false
                            )
                        }
                        Response.Loading -> {
                            copy(
                                isLoading = false
                            )
                        }
                        is Response.Success -> {
                            copy(
                                listOrders = result.data
                            )
                        }
                    }
                }
            }
        }
    }
}