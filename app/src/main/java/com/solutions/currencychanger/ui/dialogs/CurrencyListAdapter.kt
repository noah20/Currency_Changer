package com.solutions.currencychanger.ui.dialogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutions.currencychanger.databinding.CurrencyItemLayoutBinding

class CurrencyListAdapter(
    private val currencyList: List<Pair<String, Double>>,
    private val onItemClick: (Pair<String, Double>) -> Unit
) : RecyclerView.Adapter<CurrencyListAdapter.CurrencyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {

        val view =
            CurrencyItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CurrencyViewHolder(view)

    }

    override fun getItemCount() = currencyList.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bindItem(currencyList[position])
    }


    inner class CurrencyViewHolder(private val mView: CurrencyItemLayoutBinding) :
        RecyclerView.ViewHolder(mView.root) {


        fun bindItem(item: Pair<String, Double>) {
            mView.root.setOnClickListener { onItemClick(item) }
            mView.tvType.text = item.first
        }

    }


}