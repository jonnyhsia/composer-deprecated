package com.jonnyhsia.composer.page.auth.register

import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.kit.logd
import com.jonnyhsia.composer.page.base.SimplePresenter
import io.reactivex.disposables.CompositeDisposable

class RegisterPresenter(
        private val view: RegisterContract.View
) : SimplePresenter(), RegisterContract.Presenter {

    private val disposable = CompositeDisposable()

    init {
        view.bindPresenter(this)
    }

    override fun start() {
        super.start()
        view.render()
    }

    override fun clickRegister(username: String, password: String, email: String) {
        Repository.getProfileRepository().register(username, password, email,
                onSubscribe = {
                    view.showLoading()
                    disposable.add(it)
                },
                onRegisterSuccess = { user ->
                    Repository.getPassportRepository().login(user)
                    logd("${user.username}, 欢迎来到 Composer.")
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