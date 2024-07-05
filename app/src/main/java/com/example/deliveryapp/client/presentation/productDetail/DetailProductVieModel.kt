package com.example.deliveryapp.client.presentation.productDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.R
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
class DetailProductVieModel @Inject constructor(
    private val clientUseCases: ClientUseCases,
): ViewModel(){

    private val _state = MutableStateFlow(DetailProductState())
    val state: StateFlow<DetailProductState> get() = _state.asStateFlow()


    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<DetailProductEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(
        actions: DetailProductActions
    ){
        when(actions){
            DetailProductActions.OnAddCartClick -> addCart()
            DetailProductActions.OnAddProductClick -> {
                _state.update {currentState ->
                    var cout = currentState.product.quantity
                    cout++
                    currentState.copy(
                        product = currentState.product.copy(
                            quantity = cout
                        )
                    )
                }
            }
            DetailProductActions.OnRemoveProductClick -> {
                _state.update {currentState ->
                    var cout = currentState.product.quantity
                    if (currentState.product.quantity >= 1){
                        cout--
                    }
                    currentState.copy(
                        product = currentState.product.copy(
                            quantity = cout
                        )
                    )
                }
            }

            is DetailProductActions.OnReviewProductChange -> {
                _state.update {currentState ->
                    currentState.copy(
                        rating = actions.rating
                    )
                }
            }

            DetailProductActions.OnConfirmReviewProductClick -> addRating()
            else -> Unit
        }
    }

    private fun addRating(){
        viewModelScope.launch {
            val result = clientUseCases.addRatingProductUseCase(idProduct = _state.value.product.id, rating = _state.value.rating)
            when(result){
                is Response.Failure -> {
                    eventChannel.send(DetailProductEvent.Error(UiText.StringResource(R.string.error_add_rating_product)))
                }
                is Response.Success -> {
                    eventChannel.send(DetailProductEvent.Success(UiText.StringResource(R.string.success_add_rating_product)))
                }
                else -> Unit
            }
        }
    }
    private fun addCart(){
        viewModelScope.launch {
            val product = _state.value.product
            val result = clientUseCases.addProductCartUseCase(product)
            when(result){
                is Response.Failure -> {
                    eventChannel.send(DetailProductEvent.Error(UiText.StringResource(R.string.error_add_product_cart)))
                }
                is Response.Success -> {
                    eventChannel.send(DetailProductEvent.Success(UiText.StringResource(R.string.success_add_product_card)))
                }
                else -> Unit
            }
        }
    }

    fun insertProduct(product: Product){
        _state.update {currentState ->
            currentState.copy(
                product = product
            )
        }
    }
}
