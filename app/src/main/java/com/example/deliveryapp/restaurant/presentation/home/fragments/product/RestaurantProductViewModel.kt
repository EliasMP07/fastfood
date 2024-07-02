package com.example.deliveryapp.restaurant.presentation.home.fragments.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.utils.reduceImagesSize
import com.example.deliveryapp.restaurant.domain.usecases.category.RestaurantUseCases
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
class RestaurantProductViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
): ViewModel() {

    private val _state = MutableStateFlow(RestaurantProductState())
    val state: StateFlow<RestaurantProductState> get() = _state.asStateFlow()

    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<RestaurantProductEvent>()
    val events = eventChannel.receiveAsFlow()

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            restaurantUseCases.getAllCategoriesUseCase().collect{
                _state.update { currentState ->
                    currentState.copy(
                        categories = it
                    )
                }
            }
        }
    }

    private fun createProduct(){
        viewModelScope.launch {
            _state.update {currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            val product = _state.value
            val result = restaurantUseCases.createProductUseCase(
                name = product.name,
                description = product.description,
                idCategory =product.idCategory,
                images = product.image,
                price = product.price
            )

            when(result){
                is Response.Failure -> {
                    _state.update {currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(
                        RestaurantProductEvent.Error(result.exception?.message.orEmpty())
                    )
                }
                is Response.Success -> {
                    _state.update {currentState ->
                        currentState.copy(
                            name = "",
                            description = "",
                            image = listOf(),
                            idCategory = "",
                            price = 0.0,
                            isLoading = false
                        )
                    }
                    eventChannel.send(
                        RestaurantProductEvent.Success(result.data)
                    )
                }
                else -> Unit
            }
        }
    }

    fun onAction(
        action: RestaurantProductAction
    ){
        when(action){
            is RestaurantProductAction.OnImageChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        image = reduceImagesSize(action.image)
                    )
                }
            }
            is RestaurantProductAction.OnCategoryChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        idCategory = action.idCategory
                    )
                }
            }
            RestaurantProductAction.OnCreateProductClick -> createProduct()
            is RestaurantProductAction.OnDescriptionProductChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        description = action.description
                    )
                }
            }
            is RestaurantProductAction.OnNameProductChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        name = action.name
                    )
                }
            }
            is RestaurantProductAction.OnPriceProductChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        price = action.price
                    )
                }
            }
            else -> Unit
        }
    }

}