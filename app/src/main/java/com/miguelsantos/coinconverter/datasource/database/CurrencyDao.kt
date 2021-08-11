package com.miguelsantos.coinconverter.datasource.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.miguelsantos.coinconverter.datasource.model.CurrencyConversion
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Query("SELECT * FROM currencies ORDER BY id DESC")
    fun getAll(): Flow<List<CurrencyConversion>>

    @Insert
    suspend fun insertCurrency(currency: CurrencyConversion)

    @Delete
    suspend fun deleteCurrency(currency: CurrencyConversion)

    // TODO: 09/08/2021 Adicionar filtros de busca na toolbar.
}