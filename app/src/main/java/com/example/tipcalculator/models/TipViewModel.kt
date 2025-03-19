package com.example.tipcalculator.models

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
        try {
            tipAmount = amount * percentage / 100
        } catch (e: Exception) {
            tipAmount = 0.0
            println("Erro ao calcular a gorjeta personalizada: ${e.message}")
        }
    }

    fun calculateFixedTip() {
        try {
            fixedTipAmount = amount * fixedPercentage / 100
        } catch (e: Exception) {
            fixedTipAmount = 0.0
            println("Erro ao calcular a gorjeta fixa: ${e.message}")
        }
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
        try {
            _uiState.value = _uiState.value.copy(amount = amount)
            _uiState.value.calculate()
        } catch (e: Exception) {
            println("Erro ao mudar o valor da quantia: ${e.message}")
        }
    }

    fun onAmountChange(amount: String) {
        try {
            _uiState.value = _uiState.value.copy(amount = amount.toDouble())
            _uiState.value.calculate()
        } catch (e: Exception) {
            println("Erro ao mudar o valor da quantia (String): ${e.message}")
        }
    }

    fun onPercentageChange(percentage: Int) {
        try {
            _uiState.value = _uiState.value.copy(percentage = percentage)
            _uiState.value.calculate()
        } catch (e: Exception) {
            println("Erro ao mudar a porcentagem da gorjeta: ${e.message}")
        }
    }
}
