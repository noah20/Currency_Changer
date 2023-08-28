package com.solutions.currencychanger.ui.chart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutions.currencychanger.data.models.CurrencyHistoricalResponse
import com.solutions.currencychanger.databinding.HistoricalItemLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class HistoricalAdapter(
    private val historicalList: List<CurrencyHistoricalResponse>,
    private val toCurrency: String
) : RecyclerView.Adapter<HistoricalAdapter.HistoricalViewHolder>() {

private val dateFormatter = SimpleDateFormat("yyyy-MM-dd" , Locale.ENGLISH)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricalViewHolder {
        val view =
            HistoricalItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoricalViewHolder(view)
    }

    override fun getItemCount() = historicalList.size


    override fun onBindViewHolder(holder: HistoricalViewHolder, position: Int) {

        holder.bindItem(historicalList[position])
    }


    inner class HistoricalViewHolder(private val mView: HistoricalItemLayoutBinding) :
        RecyclerView.ViewHolder(mView.root) {

        fun bindItem(item: CurrencyHistoricalResponse) {
            mView.tvTitle.text = getAlternativeName(item)
        }

        private fun getAlternativeName(currencyHistoricalResponse: CurrencyHistoricalResponse): StringBuilder {
            val readableDate = android.text.format.DateFormat.format( "dd-MM-yyy",dateFormatter.parse(currencyHistoricalResponse.date))
            val title = StringBuilder(readableDate)
            title.append("\n")
                .append("1 ${currencyHistoricalResponse.base}").append(" = ")
                .append("${currencyHistoricalResponse.rates[toCurrency]} $toCurrency")
            return title
        }
    }

}