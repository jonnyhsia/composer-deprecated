package com.jonnyhsia.composer.page.auth.register

import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView

/**
 * @author JonnyHsia on 18/1/1.
 */
interface RegisterContract {

    interface Presenter : BasePresenter {

        /**
         * 点击注册
         */
        fun clickRegister(username: String, password: String, email: String)
    }

    interface View : BaseView<Presenter> {
        /** 初始化页面渲染 */
        fun render()

        /** 注册 */
        fun registerAction()

        /** 显示正在请求中 */
        fun showLoading()

        /** 完成请求 */
        fun stopLoading()
    }
}