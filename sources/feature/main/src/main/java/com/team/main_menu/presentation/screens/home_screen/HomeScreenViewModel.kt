package com.team.main_menu.presentation.screens.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team.auth.AuthPreferences
import com.team.main_menu.domain.repositories.CitiesRepository
import com.team.main_menu.domain.repositories.DeliveryRepository
import com.team.main_menu.domain.repositories.WeightLimitRepository
import com.team.main_menu.presentation.screens.home_screen.state.DeliveryUiState
import com.team.main_menu.presentation.screens.home_screen.state.PriceFilter
import com.team.main_menu.utils.cities.CityModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val authPreferences: AuthPreferences,
    private val citiesRepository: CitiesRepository,
    private val deliveryRepository: DeliveryRepository,
    private val weightLimitRepository: WeightLimitRepository,
) : ViewModel() {
    private val _sourceCityId = MutableStateFlow<Int?>(null)
    val sourceCityId: StateFlow<Int?> = _sourceCityId

    private val _lastSelectedCity = MutableStateFlow<String?>(null)
    val lastSelectedCity: StateFlow<String?> = _lastSelectedCity

    private val _targetCity = MutableStateFlow("")
    val targetCity: StateFlow<String> = _targetCity

    private val _citySearchHistory = MutableStateFlow<List<CityModel>>(emptyList())
    val citySearchHistory: StateFlow<List<CityModel>> = _citySearchHistory

    private val _loadingSuggestions = MutableStateFlow(false)
    val loadingSuggestions: StateFlow<Boolean> = _loadingSuggestions

    private val _sourceCitySuggestions = MutableStateFlow<List<CityModel>>(emptyList())
    val sourceCitySuggestions: StateFlow<List<CityModel>> = _sourceCitySuggestions

    private val _showSelectCityHint = MutableStateFlow(false)
    val showSelectCityHint: StateFlow<Boolean> = _showSelectCityHint

    private val _selectedWeightLimit = MutableStateFlow(1)
    val selectedWeightLimit: StateFlow<Int> = _selectedWeightLimit

    private val _showAiGeneratedPriceBottomSheet = MutableStateFlow(false)
    val showAiGeneratedPriceBottomSheet: StateFlow<Boolean> = _showAiGeneratedPriceBottomSheet

    private val _showAiGeneratedDurationBottomSheet = MutableStateFlow(false)
    val showAiGeneratedDurationBottomSheet: StateFlow<Boolean> = _showAiGeneratedDurationBottomSheet

    private val _showExitConfirmation = MutableStateFlow(false)
    val showExitConfirmation: StateFlow<Boolean> = _showExitConfirmation

    private var currentCitiesSearchJob: Job? = null

    private val _deliveryState = MutableStateFlow(DeliveryUiState())
    val deliveryState: StateFlow<DeliveryUiState> = _deliveryState

    init {
        viewModelScope.launch {
            _selectedWeightLimit.value = weightLimitRepository.getWeightLimit()
            _citySearchHistory.value = citiesRepository.getSearchHistory()
        }
    }

    fun showExitConfirmation() {
        _showExitConfirmation.value = true
    }

    fun confirmExit() {
        viewModelScope.launch {
            _showExitConfirmation.value = false

            authPreferences.clearAuth()
        }
    }

    fun closeExitConfirmation() {
        _showExitConfirmation.value = false
    }

    fun updateCitySearchField(value: String) {
        _targetCity.value = value

        if (value.isNotEmpty()) getSourceSuggestions()
    }

    fun onSearchFocusCanceled() {
        if (
            (lastSelectedCity.value != null && lastSelectedCity.value != targetCity.value) ||
            (lastSelectedCity.value == null && sourceCityId.value == null && targetCity.value.isNotEmpty())
        ) {
            _showSelectCityHint.value = true
            _targetCity.value = lastSelectedCity.value ?: ""
        }
    }

    fun hideSelectCityHint() {
        _showSelectCityHint.value = false
    }

    fun setSourceCity(value: CityModel) {
        viewModelScope.launch {
            _sourceCityId.value = value.id
            _lastSelectedCity.value = value.name
            _targetCity.value = value.name
            citiesRepository.saveSearchResult(value)
            resetSourceSuggestions()
            loadOffers()
        }
    }

    fun setWeightLimit(value: Int) {
        _selectedWeightLimit.value = value
        weightLimitRepository.updateWeightLimit(value)
        if (sourceCityId.value != null) loadOffers()
    }

    fun resetSourceSuggestions() {
        _sourceCitySuggestions.value = emptyList()
        currentCitiesSearchJob?.cancel()
    }

    fun showAiGeneratedPriceBottomSheet() {
        _showAiGeneratedPriceBottomSheet.value = true
    }

    fun hideAiGeneratedPriceBottomSheet() {
        _showAiGeneratedPriceBottomSheet.value = false
    }

    fun showAiGeneratedDurationBottomSheet() {
        _showAiGeneratedDurationBottomSheet.value = true
    }

    fun hideAiGeneratedDurationBottomSheet() {
        _showAiGeneratedDurationBottomSheet.value = false
    }

    private fun getSourceSuggestions() {
        currentCitiesSearchJob?.cancel()
        currentCitiesSearchJob = viewModelScope.launch(Dispatchers.IO) {
            _loadingSuggestions.value = true
            _sourceCitySuggestions.value =
                citiesRepository.searchCities(targetCity.value).getOrNull() ?: emptyList()
            _loadingSuggestions.value = false
            _citySearchHistory.value = citiesRepository.getSearchHistory()
        }
    }

    fun loadOffers() {
        _deliveryState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch(Dispatchers.IO) {
            sourceCityId.value?.let { cityId ->
                val result =
                    deliveryRepository.calculateDelivery(cityId, selectedWeightLimit.value)
                _deliveryState.update { currentState ->
                    result.fold(
                        onSuccess = { offers ->
                            currentState.copy(
                                offers = offers,
                                selectedTariff = null,
                                isLoading = false,
                                error = null
                            )
                        },
                        onFailure = { throwable ->
                            currentState.copy(
                                isLoading = false,
                                error = throwable.message
                            )
                        }
                    )
                }
            }
        }
    }

    fun selectTariff(tariff: String?) {
        _deliveryState.update { state ->
            val isSame = state.selectedTariff == tariff
            state.copy(selectedTariff = if (isSame) null else tariff)
        }
    }

    fun applyAdvancedFilter(
        priceMin: Float,
        priceMax: Float,
        maxDays: Int?
    ) {
        _deliveryState.update { state ->
            val newState = state.copy(
                priceFilter = PriceFilter(
                    min = priceMin.toBigDecimal(),
                    max = priceMax.toBigDecimal()
                ),
                maxDurationFilter = maxDays
            )
            val hasSelectedTariff = newState.selectedTariff?.let { tariff ->
                newState.filteredOffers.any { it.tariff == tariff }
            } ?: true
            if (hasSelectedTariff) newState else newState.copy(selectedTariff = null)
        }
    }

    fun clearAdvancedFilter() {
        _deliveryState.update { state ->
            state.copy(
                priceFilter = null,
                maxDurationFilter = null
            )
        }
    }

    data class DeliveryTariffFilter(
        val tariff: String,
        val count: Int,
        val isSelected: Boolean
    )
}
