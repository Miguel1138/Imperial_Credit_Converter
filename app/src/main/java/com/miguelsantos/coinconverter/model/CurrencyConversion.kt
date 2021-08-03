package com.miguelsantos.coinconverter.model

data class CurrencyConversion(
    val currencyCode: String,
    val currencyValue: String,
    val currencyConverted: String
)