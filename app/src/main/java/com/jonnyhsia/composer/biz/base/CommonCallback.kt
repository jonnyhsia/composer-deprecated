package com.jonnyhsia.composer.biz.base

import io.reactivex.disposables.Disposable

/** 通用的无入参成功回调 */
typealias OnSuccess = () -> Unit

/** 通用的失败回调 */
typealias OnFailed = (errorMessage: String?) -> Unit

/** 订阅 */
typealias OnSubscribe = (d: Disposable) -> Unit

/** 请求结束回调 (成功或失败) */
typealias OnFinally = () -> Unit