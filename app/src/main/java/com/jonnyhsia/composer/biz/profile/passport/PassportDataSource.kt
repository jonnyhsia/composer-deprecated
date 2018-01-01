package com.jonnyhsia.composer.biz.profile.passport

import com.jonnyhsia.composer.biz.profile.User

typealias LogoutCallback = () -> Unit

interface PassportDataSource {

    /**
     * @return 用户是否登录
     */
    fun isLogin(): Boolean

    /**
     * @return 获取当前登录用户
     */
    fun getLoginUser(): User?

    /**
     * @param user 登录的用户
     */
    fun login(user: User)

    /**
     * 登出账号
     */
    fun logout()

    /**
     * 获取用户 ID
     */
    fun getUserId(): String?
}