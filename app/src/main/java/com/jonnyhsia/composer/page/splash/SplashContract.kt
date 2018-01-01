package com.jonnyhsia.composer.page.splash

import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView

/**
 * @author JonnyHsia on 17/12/31.
 */
interface SplashContract {

    interface Presenter : BasePresenter {
        /**
         * 触发彩蛋
         */
        fun triggerBonusScene()
    }

    interface View : BaseView<Presenter> {
        /**
         * 初始渲染
         */
        fun render()

        /**
         * 开始动画显示
         */
        fun startAnimating()

        /**
         * 加载时间过长则显示进度条
         */
        fun showLoadingIndicator()
    }
}