package com.jonnyhsia.composer.kit

import android.util.Log
import com.jonnyhsia.composer.app.AppError
import com.jonnyhsia.composer.biz.base.Response
import com.jonnyhsia.composer.biz.base.RxHttpHandler
import io.reactivex.Single

/**
 * @author JonnyHsia on 17/12/31.
 */
fun Any.logd(message: String?, tag: String = javaClass.simpleName) {
    Log.d(tag, message)
}

fun Any.loge(message: String?, tag: String = javaClass.simpleName, e: Throwable? = null) {
    Log.e(tag, message, e)
}

fun Any.logw(message: String?, tag: String = javaClass.simpleName) {
    Log.w(tag, message)
}

fun String?.checkEmpty() = this == null || length == 0

fun String?.checkNotEmpty() = !checkEmpty()

fun Throwable.isAppException() = this is AppError.AppException

fun <T> Single<Response<T>>.handle(): Single<T> = compose(RxHttpHandler.handleSingle())
