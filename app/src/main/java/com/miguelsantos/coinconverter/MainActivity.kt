package com.miguelsantos.coinconverter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.miguelsantos.coinconverter.databinding.ActivityMainBinding
import com.miguelsantos.coinconverter.model.CurrencyConversion
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter: CurrencyAdapter by lazy {
        CurrencyAdapter()
    }
    private val list = arrayListOf<CurrencyConversion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainRecyclerConversionList.adapter = adapter
        binding.mainRecyclerConversionList.layoutManager =
            LinearLayoutManager(applicationContext)

        // Currency code Spinner
        //view Model
        val items = listOf("BRL", "USD", "EUR", "MXN", "AUD", "JPY")
        val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_currency_code_list, items
        )
        with(binding) {
            val materialAutoCompleteTextView =
                mainSpinnerCurrencyCode.editText as? MaterialAutoCompleteTextView
            materialAutoCompleteTextView?.setAdapter(itemsAdapter)

            mainBtnConvertCredit.setOnClickListener {
                showResult()
            }
        }
    }

    private fun showResult() {
        if (binding.mainTxtConversion.visibility == View.GONE) {
            binding.mainTxtConversion.visibility = View.VISIBLE
            binding.mainImageCreditSimbol.visibility = View.VISIBLE
        }

        val conversion = binding.mainEdtInputCredit.text.toString().toDoubleOrNull()
        displayConversion(conversion)
        addCurrency()
    }

    // viewModel
    private fun addCurrency() {
        val currency = CurrencyConversion(
            binding.mainSpinnerCurrencyTxt.text.toString(),
            binding.mainEdtInputCredit.text.toString(),
            binding.mainTxtConversion.text.toString()
        )
        list.add(currency)
        adapter.submitList(list)
    }

    private fun displayConversion(value: Double?) {
        if (value == null || value == 0.0) {
            binding.mainTxtConversion.text = "0.0"
            return
        } else {
            // viewModel
            val result = when (binding.mainSpinnerCurrencyTxt.text.toString()) {
                "BRL" -> value * 5.21
                "EUR" -> value * 0.84
                "USD" -> value
                "MXN" -> value * 19.88
                "AUD" -> value * 1.25
                "JPY" -> value * 109.68
                else -> 0
            }

            val formattedValue = NumberFormat.getCurrencyInstance().format(result)
            binding.mainTxtConversion.text = getString(R.string.result, formattedValue)
        }
    }
}