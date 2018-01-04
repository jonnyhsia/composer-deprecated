package com.jonnyhsia.composer.biz.profile

import com.jonnyhsia.composer.biz.base.OnFailed
import com.jonnyhsia.composer.biz.base.OnFinally
import com.jonnyhsia.composer.biz.base.OnSubscribe

typealias OnPasswordNotMatch = () -> Unit
typealias OnLoginSuccess = (User) -> Unit
typealias OnRegisterSuccess = (User) -> Unit


interface ProfileDataSource {

    /**
     * 登录
     */
    fun login(
            username: String,
            password: String,
            onSubscribe: OnSubscribe,
            onLoginSuccess: OnLoginSuccess,
            onPasswordNotMatch: OnPasswordNotMatch,
            onFailed: OnFailed,
            onFinally: OnFinally
    )

    fun register(
            username: String,
            password: String,
            email: String,
            onSubscribe: OnSubscribe,
            onRegisterSuccess: OnRegisterSuccess,
            onFailed: OnFailed,
            onFinally: OnFinally
    )
}