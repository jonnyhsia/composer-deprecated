package com.jonnyhsia.composer.page.main.inbox

class InboxPresenter(
        private val view: InboxContract.View)
    : InboxContract.Presenter {

    init {
        view.bindPresenter(this)
    }

    override fun start() {

    }

    override fun resume() {

    }

    override fun pause() {

    }

    override fun destroy() {

    }

}