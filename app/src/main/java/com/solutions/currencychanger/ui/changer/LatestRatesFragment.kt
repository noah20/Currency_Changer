package com.solutions.currencychanger.ui.changer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.solutions.currencychanger.R
import com.solutions.currencychanger.data.models.CurrencyModel
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.databinding.FragmentLatestRatesBinding
import com.solutions.currencychanger.ui.chart.KEY_BASE_CURRENCY
import com.solutions.currencychanger.ui.chart.KEY_TO_CURRENCY
import com.solutions.currencychanger.ui.dialogs.CurrencySelectorDialog
import com.solutions.currencychanger.utils.handleApiError
import com.solutions.currencychanger.utils.handleAppException
import com.solutions.currencychanger.wrapper.ResultWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LatestRatesFragment : Fragment() {

    private val viewModel: LatestRatesViewModel by activityViewModels()
    private var mBinding: FragmentLatestRatesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mBinding == null) {
            mBinding = FragmentLatestRatesBinding.inflate(inflater, container, false)
            mBinding?.viewmodel = viewModel
            mBinding?.lifecycleOwner = this
            getLatestRates("EUR")

        }
        return mBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.etToAmount?.addTextChangedListener(toTextWatcher)
        mBinding?.etFromAmount?.addTextChangedListener(fromTextWatcher)
    }

    val toTextWatcher:TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            if(count > 1 && ! mBinding?.etFromAmount?.text.isNullOrEmpty())
                return
            mBinding?.etFromAmount?.removeTextChangedListener(fromTextWatcher)
            try {
                if(!text.isNullOrEmpty()){
                    mBinding?.etToAmount?.setSelection(text.length)
                    mBinding?.etFromAmount?.setText(viewModel.getFromAmount(text.toString()))
                }
            }catch (e:Exception){

            }
            mBinding?.etFromAmount?.addTextChangedListener(fromTextWatcher)
        }
        override fun afterTextChanged(text: Editable?) {

        }
    }

    val fromTextWatcher:TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            mBinding?.etToAmount?.removeTextChangedListener(toTextWatcher)
            try {
                if(!text.isNullOrEmpty()){
                    mBinding?.etFromAmount?.setSelection(text.length)
                    val amount = text.toString()
                    viewModel.setSelectedAmount(amount)
                    mBinding?.etToAmount?.setText(viewModel.getToAmount(amount))
                }

            }catch (_ :Exception){

            }
            mBinding?.etToAmount?.addTextChangedListener(toTextWatcher)
        }
        override fun afterTextChanged(text: Editable?) {}
    }

    private fun getLatestRates(base:String) {

        lifecycleScope.launch {
            viewModel.getLatestRates(true ,base ).collect {
                mBinding?.response = it
                if (it.isSuccess) {
                    if (it.data?.success == true) {
                        updateLatestData(it.data)
                    }else{
                        activity?.handleApiError("APi Error",it.data?.error?.type)
                    }
                }else if(it.status == ResultWrapper.STATUS.FAILED){
                    it.error?.let { error -> requireActivity().handleAppException(error) }
                }
            }
        }

    }

    private fun updateLatestData(latestRates: LatestRatesResponse) {
        val baseCurrency = CurrencyModel(latestRates.base, latestRates.rates[latestRates.base] ?: 0.0)
        val toCurrency = CurrencyModel("EGP", latestRates.rates["EGP"] ?: 0.0)
        viewModel.setToCurrency( toCurrency )
        viewModel.setBaseCurrency(baseCurrency)
        mBinding?.toList?.setOnClickListener {
            val selector = CurrencySelectorDialog.newInstance(latestRates)
            selector.attachListener(object : CurrencySelectorDialog.OnCurrencySelected {
                override fun concurrencySelected(currency: Pair<String, Double>) {
                    if(viewModel.shouldFetchToCurrency(currency.first)){
                        getLatestRates(currency.first)
                    }else{
                        mBinding?.toList?.text = currency.first
                        viewModel.setToCurrency( CurrencyModel(currency.first , currency.second))
                    }
                }
            })
            selector.show(childFragmentManager , "selector")
        }
        mBinding?.fromList?.setOnClickListener {
            val selector = CurrencySelectorDialog.newInstance(latestRates)
            selector.attachListener(object : CurrencySelectorDialog.OnCurrencySelected {
                override fun concurrencySelected(currency: Pair<String, Double>) {
                    if(viewModel.shouldFetchForCurrency(currency.first)){
                        getLatestRates(currency.first)
                    }else{
                        mBinding?.fromList?.text = currency.first
                        viewModel.setBaseCurrency(CurrencyModel(currency.first , currency.second))
                    }
                }
            })
            selector.show(childFragmentManager , "selector")
        }
        mBinding?.btnSwap?.setOnClickListener {
            viewModel.swapCurrency()
        }
        mBinding?.btnDetails?.setOnClickListener {
            val data = bundleOf(
                KEY_BASE_CURRENCY to viewModel.getActualBaseCurrency(),
                KEY_TO_CURRENCY to viewModel.getActualToCurrency(),)
            findNavController().navigate(R.id.latestRate_to_historical , data)
        }
    }


}