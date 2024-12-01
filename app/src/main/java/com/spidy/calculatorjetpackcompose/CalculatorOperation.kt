package com.spidy.calculatorjetpackcompose

sealed class CalculatorOperation(val symbol:String) {
     object Add:CalculatorOperation("+")
     object Subtract:CalculatorOperation("-")
     object Multiplication:CalculatorOperation("*")
     object Divide:CalculatorOperation("/")
}