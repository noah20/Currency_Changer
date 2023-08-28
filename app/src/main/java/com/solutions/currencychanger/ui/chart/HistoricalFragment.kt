package com.solutions.currencychanger.ui.chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.solutions.currencychanger.R
import com.solutions.currencychanger.databinding.FragmentHistoricalBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

private val KEY_BASE_CURRENCY = "baseCurrency"
private val KEY_TO_CURRENCY = "toCurrency"
@AndroidEntryPoint
class HistoricalFragment : Fragment() {

    private val  viewModel: HistoricalViewModel by viewModels()
    private var mBinding:FragmentHistoricalBinding? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentHistoricalBinding.inflate(inflater,container,false)

        setupChart(mBinding?.lineChart!!)
        val currencyData = getToCurrencyData()
        updateChartWithData(mBinding?.lineChart!!,  currencyData)

        return mBinding?.root

    }

    private fun setupChart(lineChart: LineChart) {
        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
        lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
    }

    private fun updateChartWithData(lineChart: LineChart, currencyData: List<Pair<Date, Float>>) {

        val entriesCurrency2 = currencyData.mapIndexed { index, pair ->
            Entry(index.toFloat(), pair.second)
        }
        val dataSetCurrency2 = LineDataSet(entriesCurrency2, "EGP")
        dataSetCurrency2.color = resources.getColor(R.color.purple_700)
        val lineData = LineData( dataSetCurrency2)
        lineChart.data = lineData
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(getDateLabels(currencyData))
        lineChart.invalidate()
    }
    private fun getDateLabels(data: List<Pair<Date, Float>>): List<String> {
        val labels = mutableListOf<String>()
        val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
        for (pair in data) {
            labels.add(dateFormat.format(pair.first))
        }
        return labels
    }

    private fun getToCurrencyData(): List<Pair<Date, Float>> {
        val data = mutableListOf<Pair<Date, Float>>()
        val calendar = Calendar.getInstance()
        data.add(Pair(calendar.time, 33.5f))
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        data.add(Pair(calendar.time, 33.5f))
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        data.add(Pair(calendar.time, 34.0f))
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        data.add(Pair(calendar.time, 33.3f))
        return data.reversed()
    }

}