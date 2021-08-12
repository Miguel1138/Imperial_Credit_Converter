package com.miguelsantos.coinconverter.data.localdatasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.miguelsantos.coinconverter.model.CurrencyConversion
import com.miguelsantos.coinconverter.utils.Constants.currencyDBConfig.DATABASE_NAME
import com.miguelsantos.coinconverter.utils.Constants.currencyDBConfig.DATABASE_VERSION

@Database(
    entities = [CurrencyConversion::class],
    version = DATABASE_VERSION,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {
        @Volatile
        private var INSTANCE: LocalDatabase? = null

        fun getInstance(context: Context): LocalDatabase = synchronized(this) {
            var instance = INSTANCE
            if (instance == null) {
                instance = buildDatabase(context.applicationContext)
                INSTANCE = instance
            }

            return instance
        }

        private fun buildDatabase(context: Context): LocalDatabase =
            Room.databaseBuilder(context, LocalDatabase::class.java, DATABASE_NAME).build()
    }

}