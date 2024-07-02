package com.example.deliveryapp.client.presentation.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.client.domain.model.Category
import com.example.deliveryapp.client.domain.useCases.ClientUseCases
import com.example.deliveryapp.core.domain.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientProductListViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases
) : ViewModel(){

    private val _state = MutableStateFlow(ClientProductListState())
    val state: StateFlow<ClientProductListState> get() = _state.asStateFlow()


    fun getProducts(category: Category){
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    category = category
                )
            }
            clientUseCases.getAllProductByCategory(category.id).collect{productResponse ->
                when(productResponse){
                    is Response.Failure -> {
                        _state.update {currentState ->
                            currentState.copy(
                                isLoading = false
                            )
                        }
                    }
                    Response.Loading -> {
                        _state.update {currentState ->
                            currentState.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Response.Success -> {
                        _state.update {currentState ->
                            currentState.copy(
                                isLoading = false,
                                listProducts = productResponse.data
                            )
                        }
                    }
                }
            }
        }
    }

}