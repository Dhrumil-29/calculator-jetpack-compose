package com.spidy.calculatorjetpackcompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction(action: CalculatorAction) {
        println("onAction Clicked...")
        when (action) {
            is CalculatorAction.Calculate -> performCalculator()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Delete -> performDelete()
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperator(action.operator)
        }
    }

    private fun enterDecimal() {
        if (state.operation == null) {
            if (state.number1.isNotEmpty() && !state.number1.contains(
                    '.'
                )
            ) state = state.copy(number1 = state.number1 + '.')
            else if (state.number2.isNotEmpty() && !state.number2.contains(
                    '.'
                )
            ) state = state.copy(number2 = state.number2 + '.')
        }
    }

    private fun performDelete() {
        when {
            state.number2.isNotEmpty() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )

            state.operation != null -> state = state.copy(operation = null)

            state.number1.isNotEmpty() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun enterOperator(operator: CalculatorOperation) {
        if (state.number1.isNotEmpty()) {
            state = state.copy(operation = operator)
        }
    }

    private fun performCalculator() {
        val num1 = state.number1.toDoubleOrNull()
        val num2 = state.number2.toDoubleOrNull()

        if (num1 != null && num2 != null && state.operation != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> num1 + num2
                is CalculatorOperation.Divide -> num1 / num2
                is CalculatorOperation.Multiplication -> num1 * num2
                is CalculatorOperation.Subtract -> num1 - num2
                null -> return
            }
            state = CalculatorState(number1 = result.toString().take(15))
        }
    }

    private fun enterNumber(num: Int) {
        if (state.operation == null) {
            if (state.number1.length >= MAX_NUM_LENGTH) return
            state = state.copy(number1 = "${state.number1}$num")
        } else {
            if (state.number2.length >= MAX_NUM_LENGTH) return
            state = state.copy(number2 = "${state.number2}$num")
        }
    }

    companion object {
        const val MAX_NUM_LENGTH = 8
    }
}