package com.example.tipcalculator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Tip(
    val amount: Double = 0.0,
    val percentage: Int = 0,
    val fixedPercentage: Int = 15,
    var tipAmount: Double = 0.0,
    var fixedTipAmount: Double = 0.0,
) {
    fun calculateTip() {
        tipAmount = amount * percentage / 100
    }

    fun calculateFixedTip() {
        fixedTipAmount = amount * fixedPercentage / 100
    }

    fun calculate() {
        calculateTip()
        calculateFixedTip()
    }
}

class TipViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(Tip())
    val uiState: StateFlow<Tip> = _uiState.asStateFlow()

    fun onAmountChange(amount: Double) {
        _uiState.value = _uiState.value.copy(amount = amount)
        _uiState.value.calculate()
    }

    fun onAmountChange(amount: String) {
        _uiState.value = _uiState.value.copy(amount = amount.toDouble())
        _uiState.value.calculate()
    }

    fun onPercentageChange(percentage: Int) {
        _uiState.value = _uiState.value.copy(percentage = percentage)
        _uiState.value.calculate()
    }
}
