package com.miguelsantos.coinconverter.ui.viewmodel

import androidx.lifecycle.*
import com.miguelsantos.coinconverter.data.repository.CurrencyRepository
import com.miguelsantos.coinconverter.model.CurrencyConversion
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _list = repository.list.asLiveData()
    val list: LiveData<List<CurrencyConversion>>
        get() = _list

    private val _dropdownMenuItems = MutableLiveData<List<String>>()
    val dropdownMenuItems: LiveData<List<String>>
        get() = _dropdownMenuItems

    init {
        _dropdownMenuItems.value = arrayListOf("BRL", "USD", "EUR", "MXN", "AUD", "JPY")
    }

    internal fun insertCurrency(currency: CurrencyConversion) =
        viewModelScope.launch { insert(currency) }

    private suspend fun insert(currency: CurrencyConversion) {
        repository.insertCurrency(currency)
    }

    internal fun deleteCurrency(currency: CurrencyConversion) =
        viewModelScope.launch { delete(currency) }

    private suspend fun delete(currency: CurrencyConversion) {
        repository.deleteCurrency(currency)
    }

    // TODO: 11/08/2021 Search for API to get the update values of each currency.
    internal fun requestResult(result: String, value: Double): Double =
        when (result) {
            "BRL" -> value * 5.21
            "EUR" -> value * 0.84
            "USD" -> value
            "MXN" -> value * 19.88
            "AUD" -> value * 1.25
            "JPY" -> value * 109.68
            else -> 0.0
        }

}