package com.miguelsantos.coinconverter.ui

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.miguelsantos.coinconverter.R
import com.miguelsantos.coinconverter.application.CurrencyApplication
import com.miguelsantos.coinconverter.databinding.ActivityMainBinding
import com.miguelsantos.coinconverter.datasource.model.CurrencyConversion
import com.miguelsantos.coinconverter.utils.MaterialSpinnerAdapter
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val adapter by lazy { CurrencyAdapter() }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val viewModelFactory = ViewModelFactory((application as CurrencyApplication).repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        setViews()
        setObservers()
        setListeners()
    }

    private fun setViews() {
        val itemsAdapter: MaterialSpinnerAdapter<String> = MaterialSpinnerAdapter(
            this,
            R.layout.item_currency_code_list,
            viewModel.dropdownMenuItems.value!!
        )
        val materialAutoCompleteTextView =
            binding.mainSpinnerCurrencyCode.editText as? MaterialAutoCompleteTextView
        materialAutoCompleteTextView?.setAdapter(itemsAdapter)

        binding.mainRecyclerConversionList.adapter = adapter
        binding.mainRecyclerConversionList.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun setObservers() {
        viewModel.list.observe(this) { currencyList ->
            adapter.submitList(currencyList)
        }
    }

    private fun setListeners() {
        binding.mainBtnConvertCredit.setOnClickListener { showResult() }

        binding.mainEdtInputCredit.setOnKeyListener { view, keyCode, _ ->
            handlerKeyEvents(view, keyCode)
        }

        adapter.listenerDelete = { currency -> viewModel.deleteCurrency(currency) }
    }

    private fun showResult() {
        if (binding.mainTxtConversion.visibility == View.GONE) {
            binding.mainTxtConversion.visibility = View.VISIBLE
            binding.mainImageCreditSimbol.visibility = View.VISIBLE
        }

        val conversion = binding.mainEdtInputCredit.text.toString().toDoubleOrNull()
        displayConversion(conversion)
    }

    private fun displayConversion(value: Double?) {
        if (value == null || value == 0.0) {
            binding.mainTxtConversion.text = "0.0"
            return
        } else {
            val result =
                viewModel.requestResult(binding.mainSpinnerCurrencyTxt.text.toString(), value)
            val formattedValue = NumberFormat.getNumberInstance().format(result)
            binding.mainTxtConversion.text = getString(R.string.result, formattedValue)

            addCurrency()
        }
    }

    private fun addCurrency() {
        val currency = CurrencyConversion(
            binding.mainSpinnerCurrencyTxt.text.toString(),
            binding.mainEdtInputCredit.text.toString(),
            binding.mainTxtConversion.text.toString(),
            0
        )

        viewModel.insertCurrency(currency)
    }

    private fun handlerKeyEvents(view: View, key: Int): Boolean {
        if (key == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            return true
        }
        return false
    }

}