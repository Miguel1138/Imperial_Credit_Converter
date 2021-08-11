package com.miguelsantos.coinconverter.application

import android.app.Application
import com.miguelsantos.coinconverter.datasource.database.LocalDatabase
import com.miguelsantos.coinconverter.datasource.repository.CurrencyRepository

class CurrencyApplication : Application() {

    private val database by lazy { LocalDatabase.getInstance(this) }
    val repository by lazy { CurrencyRepository(database.currencyDao()) }

}