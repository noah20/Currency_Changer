package com.solutions.currencychanger.wrapper

import java.io.IOException


class NetworkException(code:Int, message: String?, case:Exception? = null) : IOException(message)