package com.miguelsantos.coinconverter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.miguelsantos.coinconverter.datasource.repository.CurrencyRepository

class ViewModelFactory(private val repository: CurrencyRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java))
            return MainViewModel(repository) as T
        throw IllegalArgumentException("Unkown view Model class")
    }
}