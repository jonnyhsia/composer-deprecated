package com.jonnyhsia.composer.page.main.timeline

import com.jonnyhsia.composer.page.base.SimplePresenter

class TimelinePresenter(
        val view: TimelineContract.View
) : SimplePresenter(), TimelineContract.Presenter {

    override fun start() {
        loadTimelineData()
    }

    private fun loadTimelineData() {

    }

}