package com.miguelsantos.coinconverter.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.miguelsantos.coinconverter.databinding.ItemConvertedValuesBinding
import com.miguelsantos.coinconverter.model.CurrencyConversion

class CurrencyAdapter :
    ListAdapter<CurrencyConversion, CurrencyAdapter.CurrencyViewHolder>(ConversionComparator()) {

    var listenerDelete: (CurrencyConversion) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding =
            ItemConvertedValuesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CurrencyViewHolder(private val binding: ItemConvertedValuesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currency: CurrencyConversion) {
            binding.itemOriginalValue.text = currency.currencyValue
            binding.itemCurrencyCode.text = currency.currencyCode
            binding.itemConvertedValue.text = currency.currencyConverted
            binding.itemDeleteCurrency.setOnClickListener { listenerDelete(currency) }
        }

        // TODO: 10/08/2021 INFLAR O MENU PARA ACIONAR O DELETE TASK.
    }

    class ConversionComparator : DiffUtil.ItemCallback<CurrencyConversion>() {

        override fun areItemsTheSame(
            oldItem: CurrencyConversion,
            newItem: CurrencyConversion
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: CurrencyConversion,
            newItem: CurrencyConversion
        ): Boolean =
            oldItem.currencyValue == newItem.currencyValue
    }

}
