package com.solutions.currencychanger.utils.binding

import android.view.TextureView
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.solutions.currencychanger.data.models.CurrencyModel


@BindingAdapter("visibilityGone")
fun visibilityGone(view: View, hide: Boolean) {

    if (hide) {
        view.visibility = View.GONE
    }else{
        view.visibility = View.VISIBLE
    }

}
@BindingAdapter("setCurrencyName")
fun setCurrencyName(tv:TextView , currency:CurrencyModel?){

    tv.text = currency?.label

}