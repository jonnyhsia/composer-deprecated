package com.jonnyhsia.composer.page.search

import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.page.base.SimplePresenter

class SearchPresenter(
        private val view: SearchContract.View
) : SimplePresenter(), SearchContract.Presenter {

    private var keyword = ""

    init {
        view.bindPresenter(this)
    }

    override fun start() {
        view.render()
    }

    override fun keywordChanged(keyword: String) {
        this.keyword = keyword

        view.changeSearchIconTint(if (keyword.isBlank()) {
            R.color.textDisable
        } else {
            R.color.textPrimary
        })
    }
}