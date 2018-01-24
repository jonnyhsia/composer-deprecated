package com.jonnyhsia.composer.page.auth.login

import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.page.base.SimplePresenter
import com.jonnyhsia.composer.router.Router
import io.reactivex.disposables.CompositeDisposable

class LoginPresenter(
        private val view: LoginContract.View
) : SimplePresenter(), LoginContract.Presenter {

    init {
        view.bindPresenter(this)
    }

    override fun start() {
        super.start()
        view.render()
    }

    override fun clickLogin(username: String, password: String) {
        Repository.getProfileRepository().login(
                username = username, password = password,
                onSubscribe = {
                    view.showLoading()
                    disposable.add(it)
                },
                onLoginSuccess = { user ->
                    Repository.getPassportRepository().login(user)
                    view.navigate("page://${Router.URI_MAIN}")
                    view.back()
                },
                onPasswordNotMatch = {
                    view.showPasswordError()
                    view.showMessage("用户名或密码错误")
                },
                onFailed = { view.showMessage(it) },
                onFinally = { view.stopLoading() }
        )
    }

    override fun destroy() {
        super.destroy()
        disposable.dispose()
    }

}