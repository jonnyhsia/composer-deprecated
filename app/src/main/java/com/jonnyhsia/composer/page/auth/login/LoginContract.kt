package com.jonnyhsia.composer.page.auth.login

import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView

/**
 * @author JonnyHsia on 18/1/1.
 */
interface LoginContract {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {
        /** 初始化页面渲染 */
        fun render()
    }
}