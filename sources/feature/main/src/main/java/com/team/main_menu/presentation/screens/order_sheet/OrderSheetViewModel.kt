package com.team.main_menu.presentation.screens.order_sheet

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.main_menu.domain.repositories.OrderHistoryRepository
import com.team.main_menu.domain.repositories.OrderRepository
import com.team.main_menu.presentation.screens.order_sheet.state.OrderSheetUiState
import com.teils.database.data.room.entities.order.OrderEntity
import com.team.main_menu.presentation.screens.order_sheet.state.ServiceUi
import com.team.main_menu.utils.order.OrderDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderSheetViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val orderHistoryRepository: OrderHistoryRepository
) : ViewModel() {
    private val _state = MutableStateFlow(OrderSheetUiState())
    val state: StateFlow<OrderSheetUiState> = _state

    fun openSheet(offerId: String, destinationCity: String) {
        _state.update {
            it.copy(
                isVisible = true,
                isLoading = true,
                error = null,
                offer = null,
                destinationCity = destinationCity,
                services = emptyList()
            )
        }

        viewModelScope.launch(Dispatchers.IO) {
            val result = orderRepository.getOrderDetails(offerId)
            _state.update { current ->
                result.fold(
                    onSuccess = { details -> current.fromDetails(details) },
                    onFailure = { throwable ->
                        Log.d("INFOG", throwable.message.toString())
                        current.copy(
                            isLoading = false,
                            error = throwable.message ?: ""
                        )
                    }
                )
            }
        }
    }

    fun toggleService(id: String) {
        _state.update { state ->
            state.copy(
                services = state.services.map { svc ->
                    if (svc.id == id) svc.copy(isSelected = !svc.isSelected) else svc
                }
            )
        }
    }

    fun pay() {
        val tracking = "DELIVERY-" + (10000..99999).random()
        _state.update { current ->
            current.copy(
                isPaid = true,
                trackingId = tracking
            )
        }
        val current = _state.value
        val offer = current.offer ?: return
        viewModelScope.launch(Dispatchers.IO) {
            orderHistoryRepository.saveOrder(
                OrderEntity(
                    trackingId = tracking,
                    companyName = offer.company.name,
                    companyLogoId = offer.company.logoId,
                    tariff = offer.tariff,
                    price = current.totalPrice?.toPlainString() ?: offer.minPrice.toPlainString(),
                    destinationCity = current.destinationCity,
                    statedDuration = offer.statedDuration,
                    predictedDuration = offer.predictedDuration
                )
            )
        }
    }

    fun close() {
        _state.value = OrderSheetUiState()
    }

    private fun OrderSheetUiState.fromDetails(details: OrderDetails): OrderSheetUiState {
        val uiServices = details.services.map {
            ServiceUi(
                id = it.id,
                name = it.name,
                price = it.price,
                isSelected = false
            )
        }
        return copy(
            isVisible = true,
            isLoading = false,
            error = null,
            offer = details.offer,
            services = uiServices
        )
    }
}
