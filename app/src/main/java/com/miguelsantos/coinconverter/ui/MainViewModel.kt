package com.miguelsantos.coinconverter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.miguelsantos.coinconverter.model.CurrencyConversion

class MainViewModel : ViewModel() {

    private val _list = MutableLiveData<MutableList<CurrencyConversion>>()
    val list: LiveData<MutableList<CurrencyConversion>>
        get() = _list

    private val _dropdownMenuItems = MutableLiveData<List<String>>()
    val dropdownMenuItems: LiveData<List<String>>
        get() = _dropdownMenuItems

    init {
        _list.value = ArrayList()
        _dropdownMenuItems.value = arrayListOf("BRL", "USD", "EUR", "MXN", "AUD", "JPY")
    }

    /**
     * Keep track of the list via the MutableLiveData and
     * update the MutableLiveData with its own value whenever the list contents change.
     */
    internal fun addCurrency(currency: CurrencyConversion) {
        _list.value?.add(currency)
        _list.value = _list.value
    }

    fun requestResult(result: String, value: Double): Double =
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