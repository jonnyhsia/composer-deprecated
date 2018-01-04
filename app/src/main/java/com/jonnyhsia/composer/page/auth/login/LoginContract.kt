package com.jonnyhsia.composer.page.auth.login

import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView

/**
 * @author JonnyHsia on 18/1/1.
 */
interface LoginContract {

    interface Presenter : BasePresenter {
        /**
         * 点击登录
         *
         * @param username 输入的用户名
         * @param password 输入的密码
         */
        fun clickLogin(username: String, password: String)

    }

    interface View : BaseView<Presenter> {
        /** 初始化页面渲染 */
        fun render()

        /** 显示正在加载中 */
        fun showLoading()

        /** 结束加载动画 */
        fun stopLoading()

        /** 登录 */
        fun loginAction()

        /**
         * 显示账号密码错误
         */
        fun showPasswordError()
    }
}