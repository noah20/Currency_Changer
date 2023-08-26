package com.solutions.currencychanger.wrapper

sealed class ResultWrapper<out T>(
    val data:T? = null,
    val errorCode:Int = -1,
    val status: STATUS = STATUS.NONE,
    val error: Throwable? = null
){

    data class Success<T>(val success:T): ResultWrapper<T>(data = success , status = STATUS.SUCCESS)

    data class Error<T : Throwable>(
        val code:  Int = -1 ,
        val throwable: T) : ResultWrapper<Nothing>(data = null, errorCode = code , status = STATUS.FAILED, error = throwable )


    class Loading (): ResultWrapper<Nothing>(status = STATUS.LOADING)

    class Canceled (): ResultWrapper<Nothing>(status = STATUS.CANCELED)


    val isSuccess: Boolean get() = status == STATUS.SUCCESS

    val isLoading: Boolean get() = status == STATUS.LOADING



    enum class STATUS{

        LOADING , CANCELED , SUCCESS , FAILED , NONE
    }

}
