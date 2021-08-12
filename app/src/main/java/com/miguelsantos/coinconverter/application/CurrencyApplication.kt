package com.miguelsantos.coinconverter.application

import android.app.Application
import com.miguelsantos.coinconverter.data.localdatasource.LocalDatabase
import com.miguelsantos.coinconverter.data.repository.CurrencyRepository

class CurrencyApplication : Application() {

    private val database by lazy { LocalDatabase.getInstance(this) }
    val repository by lazy { CurrencyRepository(database.currencyDao()) }

}