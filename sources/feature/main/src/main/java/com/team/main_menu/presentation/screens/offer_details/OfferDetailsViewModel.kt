package com.team.main_menu.presentation.screens.offer_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.main_menu.domain.repositories.DeliveryRepository
import com.team.main_menu.presentation.screens.offer_details.state.OfferDetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfferDetailsViewModel @Inject constructor(
    private val deliveryRepository: DeliveryRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(OfferDetailsUiState())
    val state: StateFlow<OfferDetailsUiState> = _state

    fun loadOffer(id: String) {
        _state.value = _state.value.copy(isLoading = true, error = null)
        viewModelScope.launch(Dispatchers.IO) {
            val result = deliveryRepository.getOfferById(id)
            _state.value = result.fold(
                onSuccess = {
                    OfferDetailsUiState(offer = it, isLoading = false) },
                onFailure = {
                    OfferDetailsUiState(error = it.message ?: "Не удалось загрузить оффер")
                }
            )
        }
    }
}