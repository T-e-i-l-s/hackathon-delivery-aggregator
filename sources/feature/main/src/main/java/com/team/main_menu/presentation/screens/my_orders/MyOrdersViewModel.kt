package com.team.main_menu.presentation.screens.my_orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.main_menu.data.repositories.FakeDeliveryData
import com.team.main_menu.domain.repositories.OrderHistoryRepository
import com.team.network.MockConfig
import com.teils.database.data.room.entities.order.OrderEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val orderHistoryRepository: OrderHistoryRepository
) : ViewModel() {
    val orders: StateFlow<List<OrderEntity>> = orderHistoryRepository.getOrders()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        if (MockConfig.USE_MOCK_DATA) {
            viewModelScope.launch(Dispatchers.IO) {
                val existing = orderHistoryRepository.getOrders().first()
                if (existing.isEmpty()) {
                    FakeDeliveryData.fakeOrders.forEach {
                        orderHistoryRepository.saveOrder(it)
                    }
                }
            }
        }
    }
}
