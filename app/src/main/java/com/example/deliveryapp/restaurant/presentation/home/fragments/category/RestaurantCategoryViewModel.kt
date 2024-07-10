package com.example.deliveryapp.restaurant.presentation.home.fragments.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deliveryapp.core.domain.model.Response
import com.example.deliveryapp.core.presentation.ui.utils.imageCamara
import com.example.deliveryapp.core.presentation.ui.utils.reduceImageSize
import com.example.deliveryapp.restaurant.domain.model.CategoryRequest
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
class RestaurantCategoryViewModel @Inject constructor(
    private val restaurantUseCases: RestaurantUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(RestaurantCategoryState())
    val state: StateFlow<RestaurantCategoryState> get() = _state.asStateFlow()

    //Variable que contiene los eventos del registro
    private val eventChannel = Channel<RestaurantCategoryEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(
        action: RestaurantCategoryAction
    ) {
        when (action) {
            RestaurantCategoryAction.OnCreateCategoryClick -> createCategory()
            is RestaurantCategoryAction.OnImageCamaraChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        imagePreview = action.image,
                        image = imageCamara(action.image)
                    )
                }
            }

            is RestaurantCategoryAction.OnImageGalleryChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        imagePreview = action.image,
                        image = reduceImageSize(action.image),
                    )
                }
            }

            is RestaurantCategoryAction.OnNameCategoryChange -> {
                _state.update { currentState ->
                    currentState.copy(
                        categoryName = action.name
                    )
                }
            }

            else -> Unit
        }
    }


    private fun createCategory() {
        viewModelScope.launch {
            _state.update { currentState ->
                currentState.copy(
                    isLoading = true
                )
            }
            val result = restaurantUseCases.createCategoryUseCase(
                CategoryRequest(
                    image = _state.value.image,
                    name = _state.value.categoryName
                )
            )
            when (result) {
                is Response.Failure -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false
                        )
                    }
                    eventChannel.send(
                        RestaurantCategoryEvent.Error(
                            result.error
                        )
                    )
                }
                is Response.Success -> {
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            categoryName = "",
                            imagePreview = "",
                            image = ""
                        )
                    }
                    eventChannel.send(RestaurantCategoryEvent.OnSuccess)
                }
                else -> Unit
            }
        }
    }
}