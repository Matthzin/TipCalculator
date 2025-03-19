package com.example.tipcalculator.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tipcalculator.components.Block
import com.example.tipcalculator.models.TipViewModel

@Composable
fun MainScreen() {
    val tipViewModel: TipViewModel = try {
        viewModel()
    } catch (e: Exception) {
        Log.e("MainScreen", "Erro ao inicializar o ViewModel", e)
        TipViewModel()
    }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TipFields(tipViewModel)
            TipShowFields(tipViewModel)
        }
    }
}

@Composable
fun TipFields(tipViewModel: TipViewModel) {
    val tip = tipViewModel.uiState.collectAsState()

    val lightGrayColor = Color(0xFFB0BEC5)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Amount", modifier = Modifier.weight(1f))
            OutlinedTextField(
                value = tip.value.amount.toString(),
                onValueChange = {
                    if (it.all { char -> char.isDigit() || char == '.' }) {
                        tipViewModel.onAmountChange(it)
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = lightGrayColor,
                    unfocusedContainerColor = lightGrayColor,
                    disabledContainerColor = lightGrayColor,
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.Gray
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(vertical = 8.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Custom %: ", modifier = Modifier.weight(1f))
            Slider(
                value = tip.value.percentage.toFloat(),
                onValueChange = {
                    tipViewModel.onPercentageChange(it.toInt())
                },
                valueRange = 0f..30f,
                steps = 30,
                colors = SliderDefaults.colors(
                    thumbColor = lightGrayColor,
                    activeTrackColor = lightGrayColor,
                    inactiveTrackColor = lightGrayColor.copy(alpha = 0.3f)
                ),
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .padding(8.dp)
            )
        }
    }
}

@Composable
fun TipShowFields(tipViewModel: TipViewModel) {
    val tip = tipViewModel.uiState.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {}
        item {
            Text(text = "${tip.value.fixedPercentage}%")
        }
        item {
            Text(text = "${tip.value.percentage}%")
        }
        item {
            Text(text = "Tip")
        }
        item {
            Block {
                Text(text = "$${tip.value.fixedTipAmount}")
            }
        }
        item {
            Block {
                Text(text = "$${tip.value.tipAmount}")
            }
        }
        item {
            Text(text = "Total")
        }
        item {
            Block {
                Text(text = "$${tip.value.amount + tip.value.fixedTipAmount}")
            }
        }
        item {
            Block {
                Text(text = "$${tip.value.amount + tip.value.tipAmount}")
            }
        }
    }
}
