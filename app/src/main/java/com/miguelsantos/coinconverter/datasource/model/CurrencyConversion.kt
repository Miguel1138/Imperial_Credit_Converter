package com.miguelsantos.coinconverter.datasource.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miguelsantos.coinconverter.utils.Constants.currencyDBConfig.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class CurrencyConversion(
    val currencyCode: String,
    val currencyValue: String,
    val currencyConverted: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 1
)