package com.example.deliveryapp.client.presentation.cartShopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
import com.example.deliveryapp.client.domain.model.CartShopping
import com.example.deliveryapp.client.domain.model.Product
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
class ClientCartViewModel @Inject constructor(
    private val clientUseCases: ClientUseCases
): ViewModel() {


    private val _state = MutableStateFlow(ClientCartState())
    val state: StateFlow<ClientCartState> get() = _state.asStateFlow()

    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<ClientCartEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getProductsCart()
    }

    private fun updateAllCart(){
        viewModelScope.launch {
            clientUseCases.updateAllCartUseCase(
                CartShopping(
                    listProducts = _state.value.productCart
                )
            )
        }
    }

    private fun getProductsCart(){
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    productCart = clientUseCases.getCartShopping()
                )
            }
        }
    }

    fun onAction(
        action: ClientCartAction
    ){
        when(action){
            is ClientCartAction.OnAddQuantityCLick -> {
                updateProductQuantity(action.product.id, 1)
            }
            is ClientCartAction.OnRemoveProductInCartClick -> {
                removeProductCart(product = action.product)
                removeProductFromCart(action.product.id)
            }
            is ClientCartAction.OnRemoveQuantityCLick -> {
                updateProductQuantity(action.product.id,-1)
            }
            ClientCartAction.OnUpdateAllCart -> updateAllCart()
            else -> Unit
        }
    }


    private fun removeProductCart(product: Product){
        viewModelScope.launch {
            val result = clientUseCases.removeProductToCartUseCase(product)
            when(result){
                is Response.Failure -> {
                    eventChannel.send(ClientCartEvent.Error(UiText.StringResource(R.string.error_remove_product)))
                }
                is Response.Success -> {
                    eventChannel.send(ClientCartEvent.Success(UiText.StringResource(R.string.success_remove_product)))
                }
                else -> Unit
            }
        }
    }

    private fun updateProductQuantity(idProduct: String, quantityChange: Int) {
        _state.update { currentState ->
            val newList = currentState.productCart.map { product ->
                if (product.id == idProduct) {
                    val newQuantity = (product.quantity + quantityChange).coerceAtLeast(1)
                    product.copy(quantity = newQuantity)
                } else {
                    product
                }
            }
            currentState.copy(
                productCart = newList
            )
        }
    }


    private fun removeProductFromCart(idProduct: String) {
        _state.update { currentState ->
            val newList = currentState.productCart.filter { it.id != idProduct }
            currentState.copy(
                productCart = newList
            )
        }
    }

}