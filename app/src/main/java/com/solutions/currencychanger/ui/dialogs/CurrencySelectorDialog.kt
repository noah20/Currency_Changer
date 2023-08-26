package com.solutions.currencychanger.ui.dialogs

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.solutions.currencychanger.data.models.LatestRatesResponse
import com.solutions.currencychanger.databinding.FragmentCurrencySelectorDialogBinding


private const val ARG_LATEST_RATES = "latest-rates"

class CurrencySelectorDialog : BottomSheetDialogFragment() {

    private var mBinding:FragmentCurrencySelectorDialogBinding? = null
    private val mLatestRates:LatestRatesResponse? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_LATEST_RATES , LatestRatesResponse::class.java)
        }else{
            arguments?.getParcelable(ARG_LATEST_RATES) as? LatestRatesResponse
        }
    }

    private var selectionListener:OnCurrencySelected? = null

    interface OnCurrencySelected{
        fun concurrencySelected(currency:Pair<String,Double>)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mBinding = FragmentCurrencySelectorDialogBinding.inflate(inflater,container,false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable())

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CurrencyListAdapter(mLatestRates?.rates?.toList() ?: emptyList()){
            dismiss()
            selectionListener?.concurrencySelected(it)
        }
        mBinding?.rvCurrency?.adapter = adapter
    }

    fun attachListener(selectionListener:OnCurrencySelected){
        this.selectionListener = selectionListener
    }

    companion object {
        @JvmStatic
        fun newInstance(latestRatesResponse: LatestRatesResponse) =
            CurrencySelectorDialog().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_LATEST_RATES, latestRatesResponse)
                }
            }
    }
}