package com.jonnyhsia.composer.page.auth.register

import com.jonnyhsia.composer.page.base.SimplePresenter

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

}