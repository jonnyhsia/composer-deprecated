package com.jonnyhsia.composer.page.creativework

import com.jonnyhsia.composer.page.base.SimplePresenter

class CreativeWorkPresenter(
        private val view: CreativeWorkContract.View
) : SimplePresenter(), CreativeWorkContract.Presenter {

    init {
        view.bindPresenter(this)
    }
}