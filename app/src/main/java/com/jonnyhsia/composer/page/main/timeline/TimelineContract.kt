package com.jonnyhsia.composer.page.main.timeline

import com.jonnyhsia.composer.app.Models
import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView
import me.drakeet.multitype.Items

/**
 * @author JonnyHsia on 18/1/5.
 */
interface TimelineContract {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

        /**
         * 初始化 View
         */
        fun initialize(timelineModels: Models)

        /**
         * 显示时间线数据
         * @param timelineData 数据
         */
        fun showTimeline(timelineData: Items)
    }
}