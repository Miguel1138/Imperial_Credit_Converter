package com.miguelsantos.coinconverter.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.miguelsantos.coinconverter.R
import com.miguelsantos.coinconverter.databinding.ActivityMainBinding
import com.miguelsantos.coinconverter.model.CurrencyConversion
import com.miguelsantos.coinconverter.utils.MaterialSpinnerAdapter
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter: CurrencyAdapter by lazy {
        CurrencyAdapter()
    }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.mainRecyclerConversionList.adapter = adapter
        binding.mainRecyclerConversionList.layoutManager =
            LinearLayoutManager(applicationContext)

        val itemsAdapter: MaterialSpinnerAdapter<String> = MaterialSpinnerAdapter(
            this,
            R.layout.item_currency_code_list,
            viewModel.dropdownMenuItems.value!!
        )
        val materialAutoCompleteTextView =
            binding.mainSpinnerCurrencyCode.editText as? MaterialAutoCompleteTextView
        materialAutoCompleteTextView?.setAdapter(itemsAdapter)

        setObservers()

        binding.mainBtnConvertCredit.setOnClickListener { showResult() }

    }

    private fun setObservers() {
        viewModel.list.observe(this) { currencyList ->
            adapter.submitList(currencyList)
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
        viewModel.addCurrency(currency)
    }

    private fun displayConversion(value: Double?) {
        if (value == null || value == 0.0) {
            binding.mainTxtConversion.text = "0.0"
            return
        } else {
            val result =
                viewModel.requestResult(binding.mainSpinnerCurrencyTxt.text.toString(), value)
            val formattedValue = NumberFormat.getCurrencyInstance().format(result)
            binding.mainTxtConversion.text = getString(R.string.result, formattedValue)
        }
    }
}