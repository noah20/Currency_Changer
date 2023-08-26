package com.solutions.currencychanger.wrapper


data class AppException(val errorCode:Int = -1, override val message : String, val exception: Exception? = null):Exception() {

}