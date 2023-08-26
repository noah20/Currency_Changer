package com.solutions.currencychanger.utils.binding

import android.view.View
import androidx.databinding.BindingAdapter


@BindingAdapter("visibilityGone")
fun visibilityGone(view: View, hide: Boolean) {

    if (hide) {
        view.visibility = View.GONE
    }else{
        view.visibility = View.VISIBLE
    }

}