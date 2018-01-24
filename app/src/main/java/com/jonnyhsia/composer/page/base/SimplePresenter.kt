package com.jonnyhsia.composer.page.base

import io.reactivex.disposables.CompositeDisposable

/**
 * @author JonnyHsia on 17/12/31.
 */
open class SimplePresenter : BasePresenter {

    protected val disposable by lazy {
        CompositeDisposable()
    }

    override fun start() {
    }

    override fun resume() {
    }

    override fun pause() {
    }

    override fun destroy() {
        disposable.dispose()
    }
}