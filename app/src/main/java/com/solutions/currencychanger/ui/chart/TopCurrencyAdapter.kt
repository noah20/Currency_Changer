package com.solutions.currencychanger.ui.chart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutions.currencychanger.databinding.HistoricalItemLayoutBinding


class TopCurrencyAdapter(private val topCurrencyList: List<Pair<String,Double>>, private val baseCurrency: String) : RecyclerView.Adapter<TopCurrencyAdapter.TopCurrencyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopCurrencyViewHolder {
        val view =
            HistoricalItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopCurrencyViewHolder(view)
    }

    override fun getItemCount() = topCurrencyList.size


    override fun onBindViewHolder(holder: TopCurrencyViewHolder, position: Int) {
        holder.bindItem(topCurrencyList[position])
    }

    inner class TopCurrencyViewHolder(private val mView: HistoricalItemLayoutBinding) : RecyclerView.ViewHolder(mView.root) {
        fun bindItem(item: Pair<String, Double>)  {
            mView.tvTitle.text = getAlternativeName(item)
        }

        private fun getAlternativeName(currency: Pair<String, Double>): String {
            val title = StringBuilder()
            title.append("1 $baseCurrency").append(" = ")
                .append("${currency.second} ${currency.first}")
            return title.toString()
        }
    }

}