package com.miguelsantos.coinconverter.datasource.repository

import androidx.room.Delete
import androidx.room.Insert
import com.miguelsantos.coinconverter.datasource.database.CurrencyDao
import com.miguelsantos.coinconverter.datasource.model.CurrencyConversion

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    val list = currencyDao.getAll()

    @Insert
    suspend fun insertCurrency(currency: CurrencyConversion) {
        currencyDao.insertCurrency(currency)
    }

    @Delete
    suspend fun deleteCurrency(currency: CurrencyConversion) {
        currencyDao.deleteCurrency(currency)
    }
}