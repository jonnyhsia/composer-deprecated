package com.jonnyhsia.composer.kit

import android.util.Log
import android.util.TypedValue
import com.jonnyhsia.composer.app.App
import com.jonnyhsia.composer.app.AppError
import com.jonnyhsia.composer.biz.base.Response
import com.jonnyhsia.composer.biz.base.RxHttpHandler
import io.reactivex.Single

@Deprecated("功能过于简陋, 不再推荐使用",
        ReplaceWith("Debug.d(tag, message)", "com.jonnyhsia.composer.kit.Debug"))
fun Any.logd(message: String?, tag: String = javaClass.simpleName) {
    Log.d(tag, message)
}

@Deprecated("功能过于简陋, 不再推荐使用",
        ReplaceWith("Debug.d(tag, message)", "com.jonnyhsia.composer.kit.Debug"))
fun Any.loge(message: String?, tag: String = javaClass.simpleName, e: Throwable? = null) {
    Log.e(tag, message, e)
}

@Deprecated("功能过于简陋, 不再推荐使用",
        ReplaceWith("Debug.d(tag, message)", "com.jonnyhsia.composer.kit.Debug"))
fun Any.logw(message: String?, tag: String = javaClass.simpleName) {
    Log.w(tag, message)
}

fun String?.checkEmpty() = this == null || length == 0

fun String?.checkNotEmpty() = !checkEmpty()

fun Throwable.isAppException() = this is AppError.AppException

fun <T> Single<Response<T>>.handle(): Single<T> = compose(RxHttpHandler.handleSingle())

fun Int.sp2px(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), App.INSTANCE.resources.displayMetrics).toInt()

fun Int.dp2Px(): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), App.INSTANCE.resources.displayMetrics).toInt()