package com.solutions.currencychanger.utils

import android.app.Activity
import android.app.AlertDialog
import com.solutions.currencychanger.R
import com.solutions.currencychanger.wrapper.AppException
import com.solutions.currencychanger.wrapper.ExceptionCode

fun Activity.handleApiError(title:String, des: String?){

    val dialog = AlertDialog.Builder(this)
        .setTitle(title)
        .setMessage(des).setPositiveButton(getString(R.string.str_ok)
        ) { dialog, _ -> dialog?.dismiss() }
    dialog.show()


}

fun Activity.handleAppException(exception: Throwable){

    val des:String = if(exception is AppException){
        if(exception.errorCode == ExceptionCode.EXCEPTION_CODE_NO_INTERNET){
            getString(R.string.str_no_connection)
        }else{
            exception.message
        }
    }else{
        exception.message ?: "Some Thing went Wrong"
    }
    val dialog = AlertDialog.Builder(this)
        .setTitle(getString(R.string.str_error))
        .setMessage(des).setPositiveButton(getString(R.string.str_ok)
        ) { dialog, _ -> dialog?.dismiss() }
    dialog.show()

}