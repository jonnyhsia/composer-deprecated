package com.jonnyhsia.composer.page.search

import android.support.annotation.ColorRes
import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView

interface SearchContract {

    interface Presenter : BasePresenter {
        /**
         * 搜索的关键词更改了
         * @param keyword 搜索关键词
         */
        fun keywordChanged(keyword: String)
    }

    interface View : BaseView<Presenter> {
        /**
         * 初始化渲染
         */
        fun render()

        /**
         * 更改搜索按钮颜色
         * @param tintColorRes 颜色的资源 ID
         */
        fun changeSearchIconTint(@ColorRes tintColorRes: Int)
    }

}