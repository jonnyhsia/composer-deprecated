package com.jonnyhsia.composer.biz.config

/**
 * @author JonnyHsia on 18/1/4.
 */
interface ConfigDataSource {

    /**
     * 标记不是第一次打开 App
     */
    fun markNotFirstOpenApp()

    /**
     * @return 返回是否是第一次打开 App
     */
    fun isFirstOpenApp(): Boolean

    /**
     * 标记已通过 Auth Page
     */
    fun markHavePassedAuthPage()

    /**
     * @return 返回 Auth page 是否通过了
     */
    fun getAuthPageHavePassed(): Boolean
}