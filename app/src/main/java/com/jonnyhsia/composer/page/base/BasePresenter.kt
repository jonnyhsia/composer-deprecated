package com.jonnyhsia.composer.page.base

/**
 * @author JonnyHsia on 17/12/31.
 */
interface BasePresenter {

    /**
     * 第一次启动
     */
    fun start()

    /**
     * 回到页面
     */
    fun resume()

    /**
     * 销毁
     */
    fun destroy()
}