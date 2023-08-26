package com.solutions.currencychanger.utils

import android.app.Activity
import android.app.AlertDialog
import com.solutions.currencychanger.R

fun Activity.handleApiError(title:String, des: String?){

    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(des).setPositiveButton(getString(R.string.str_ok)
        ) { dialog, _ -> dialog?.dismiss() }
    dialog.show()


}