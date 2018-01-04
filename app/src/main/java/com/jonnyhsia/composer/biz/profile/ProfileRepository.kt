package com.jonnyhsia.composer.biz.profile

import com.jonnyhsia.composer.app.AppError
import com.jonnyhsia.composer.biz.base.OnFailed
import com.jonnyhsia.composer.biz.base.OnFinally
import com.jonnyhsia.composer.biz.base.OnSubscribe
import com.jonnyhsia.composer.biz.base.RxHttpHandler
import com.jonnyhsia.composer.biz.base.RxHttpSchedulers
import com.jonnyhsia.composer.biz.base.BaseLogic.Companion.retrofit

class ProfileRepository : ProfileDataSource {

    private val profileApi: ProfileApi = retrofit.create(ProfileApi::class.java)

    override fun login(
            username: String,
            password: String,
            onSubscribe: OnSubscribe,
            onLoginSuccess: OnLoginSuccess,
            onPasswordNotMatch: OnPasswordNotMatch,
            onFailed: OnFailed,
            onFinally: OnFinally
    ) {
        profileApi.login(username, password)
                .compose(RxHttpSchedulers.composeSingle())
                .compose(RxHttpHandler.handleSingle())
                .doOnSubscribe(onSubscribe)
                .doFinally(onFinally)
                .subscribe(onLoginSuccess) { e ->
                    if (e is AppError.AppException
                            && e.code == AppError.PASSWORD_NOT_MATCH_USERNAME.code()) {
                        onPasswordNotMatch()
                    } else {
                        onFailed(e.message)
                    }
                }
    }

    override fun register(username: String,
                          password: String,
                          email: String,
                          onSubscribe: OnSubscribe,
                          onRegisterSuccess: OnRegisterSuccess,
                          onFailed: OnFailed,
                          onFinally: OnFinally) {
        val params = mapOf(
                "username" to username,
                "password" to password,
                "email" to email
        )

        profileApi.register(params)
                .compose(RxHttpSchedulers.composeSingle())
                .compose(RxHttpHandler.handleSingle())
                .doOnSubscribe(onSubscribe)
                .doFinally(onFinally)
                .subscribe(onRegisterSuccess) { e ->
                    if (e is AppError.AppException) {
                        onFailed(e.message)
                    } else {
                        onFailed(e.message)
                    }
                }
    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance: ProfileRepository = ProfileRepository()
    }
}