package com.jonnyhsia.composer.page.auth.login

import com.jonnyhsia.composer.page.auth.register.RegisterContract
import com.jonnyhsia.composer.page.base.SimplePresenter

class LoginPresenter(
        private val view: RegisterContract.View
) : SimplePresenter(), RegisterContract.Presenter {

    init {
        view.bindPresenter(this)
    }

    override fun start() {
        super.start()
        view.render()
    }

}