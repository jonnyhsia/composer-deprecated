package com.jonnyhsia.composer.page.base

import android.widget.Toast
import com.jonnyhsia.composer.router.Router

/**
 * View 基础接口
 * @author JonnyHsia on 17/12/31.
 */
interface BaseView<in P : BasePresenter> {

    /**
     * 绑定 Presenter
     */
    fun bindPresenter(presenter: P)

    /**
     * 显示信息
     */
    fun showMessage(message: String?, duration: Int = Toast.LENGTH_SHORT)

    /**
     * 页面路由
     */
    fun navigate(pageUriString: String)

    /**
     * 页面路由
     */
    fun router(pageUriString: String): Router.Builder

    /**
     * 返回
     */
    fun back()
}