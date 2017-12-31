package com.jonnyhsia.composer.page.base

/**
 * @author JonnyHsia on 17/12/31.
 */
interface BaseView<in P : BasePresenter> {

    /**
     * 绑定 Presenter
     */
    fun bindPresenter(presenter: P)

    /**
     * 页面路由
     */
    fun navigate(pageUriString: String)
}