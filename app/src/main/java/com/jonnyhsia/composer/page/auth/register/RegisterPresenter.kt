package com.jonnyhsia.composer.page.auth.register

import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.page.base.SimplePresenter
import com.jonnyhsia.composer.router.Router
import io.reactivex.disposables.CompositeDisposable

class RegisterPresenter(
        private val view: RegisterContract.View
) : SimplePresenter(), RegisterContract.Presenter {

    init {
        view.bindPresenter(this)
    }

    override fun start() {
        super.start()
        view.render()
    }

    override fun clickRegister(username: String, password: String, email: String) {
        if (username.isBlank() || password.isBlank() || email.isBlank()) {
            return
        }

        Repository.getProfileRepository().register(username, password, email,
                onSubscribe = {
                    view.showLoading()
                    disposable.add(it)
                },
                onRegisterSuccess = { user ->
                    Repository.getPassportRepository().login(user)
                    view.showMessage("${user.username}, 欢迎来到 Composer.")
                    view.navigate("page://${Router.URI_MAIN}")
                    view.back()
                },
                onFailed = {
                    view.showMessage(it)
                },
                onFinally = {
                    view.stopLoading()
                })
    }

    override fun destroy() {
        super.destroy()
        disposable.dispose()
    }
}