package com.miguelsantos.coinconverter

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.miguelsantos.coinconverter.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var items: List<String>

    // TODO: 30/07/2021 Adicionar RecyclerView na mainActivity com os útlimos valores convertidos. 
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Currency code Spinner
        items = listOf("BRL", "USD", "EUR", "MXN", "AUD", "JPY")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this,
            R.layout.item_currency_code_list, items
        )
        val materialAutoCompleteTextView =
            binding.mainSpinnerCurrencyCode.editText as? MaterialAutoCompleteTextView
        materialAutoCompleteTextView?.setAdapter(adapter)
        
        with(binding) {
            mainBtnConvertCredit.setOnClickListener {
                showResult()
            }
        }
    }

    private fun showResult() {
        binding.mainTxtConversion.visibility = View.VISIBLE
        binding.mainImageCreditSimbol.visibility = View.VISIBLE

        val conversion = binding.mainEdtInputCredit.text.toString().toDoubleOrNull()
        displayConversion(conversion)
    }

    private fun displayConversion(value: Double?) {
        if (value == null || value == 0.0) {
            binding.mainTxtConversion.text = "0.0"
            return
        } else {
            val result = when (binding.mainSpinnerCurrencyTxt.text.toString()) {
            // TODO: 30/07/2021 Trocar por equals ou outro método para comparar os valores das strings
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