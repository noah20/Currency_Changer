package com.solutions.currencychanger.ui.chart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.solutions.currencychanger.R
import com.solutions.currencychanger.data.models.CurrencyHistoricalResponse
import com.solutions.currencychanger.databinding.FragmentHistoricalBinding
import com.solutions.currencychanger.utils.handleAppException
import com.solutions.currencychanger.wrapper.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

 val KEY_BASE_CURRENCY = "baseCurrency"
 val KEY_TO_CURRENCY = "toCurrency"
@AndroidEntryPoint
class HistoricalFragment : Fragment() {

    private val  viewModel: HistoricalViewModel by viewModels()
    private var mBinding:FragmentHistoricalBinding? = null
    private val baseCurrency by lazy {
        arguments?.getString(KEY_BASE_CURRENCY) ?: ""
    }
    private val toCurrency by lazy {
        arguments?.getString(KEY_TO_CURRENCY) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentHistoricalBinding.inflate(inflater,container,false)
        setupChart(mBinding?.lineChart!!)

       lifecycleScope.launch {
           viewModel.getHistorical(baseCurrency , arrayOf(toCurrency)).collect{
               mBinding?.response = it
               if (it.isSuccess) {
                   val currencyData = viewModel.getToCurrencyData(it.data ?: emptyList() , toCurrency)
                   updateChartWithData(mBinding?.lineChart!!,  currencyData)
                   updateHistoricalList(it.data)
               }else  if(it.status == ResultWrapper.STATUS.FAILED){
                   it.error?.let { error -> requireActivity().handleAppException(error) }
               }
           }
       }
        lifecycleScope.launch {

            viewModel.getLatestRateForCurrency(baseCurrency).collect{

            }
        }

        return mBinding?.root

    }

    private fun updateHistoricalList(currencyData: List<CurrencyHistoricalResponse>?) {
        mBinding?.rvHistorical?.adapter = HistoricalAdapter(currencyData ?: emptyList() , toCurrency)
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
        val dataSetCurrency2 = LineDataSet(entriesCurrency2, toCurrency)
        dataSetCurrency2.color = resources.getColor(R.color.purple_700)
        val lineData = LineData( dataSetCurrency2)
        lineChart.data = lineData
        lineChart.xAxis.valueFormatter = IndexAxisValueFormatter(viewModel.getDateLabels(currencyData))
        lineChart.invalidate()
    }

}