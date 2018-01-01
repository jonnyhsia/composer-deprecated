package com.jonnyhsia.composer.biz

import io.reactivex.disposables.Disposable

/** 通用的无入参成功回调 */
typealias OnSuccess = () -> Unit

/** 通用的失败回调 */
typealias OnFailed = (error: Int, errorMessage: String?) -> Unit

/** 订阅 */
typealias OnSubscribe = (d: Disposable) -> Unit