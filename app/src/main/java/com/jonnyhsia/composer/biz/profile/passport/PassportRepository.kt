package com.jonnyhsia.composer.biz.profile.passport

import android.content.Context
import com.jonnyhsia.composer.biz.BaseLogic
import com.jonnyhsia.composer.biz.profile.User

/**
 * @author JonnyHsia on 17/12/31.
 */
class PassportRepository private constructor() : BaseLogic(), PassportDataSource {

    override fun isLogin(): Boolean {
        return false
    }

    override fun getLoginUser(): User? {
        return null
    }

    override fun login(user: User) {
        val prefPassport = context?.getSharedPreferences(NAME_PASSPORT, Context.MODE_PRIVATE)
                ?: throw IllegalStateException("用户信息存储失败")

        prefPassport.edit()
                .putString(PREF_USERNAME, user.username)
                .putString(PREF_NICKNAME, user.nickname)
                .putString(PREF_AVATAR, user.avatar)
                .putString(PREF_MOBILE, user.mobile)
                .putString(PREF_EMAIL, user.email)
                .putString(PREF_TOKEN, user.token)
                .apply()
    }

    override fun logout() {
        // 清除登录的信息, 并注销登录时的缓存数据
        context?.getSharedPreferences(NAME_PASSPORT, Context.MODE_PRIVATE)?.edit()?.clear()?.apply()
        registeredCaches.forEach { it.invoke() }
    }

    override fun getUserId() = context?.getSharedPreferences(NAME_PASSPORT, Context.MODE_PRIVATE)?.getString(PREF_USERNAME, null)

    companion object {
        const val NAME_PASSPORT = "passport"
        const val PREF_USERNAME = "username"
        const val PREF_NICKNAME = "nickname"
        const val PREF_AVATAR = "avatar"
        const val PREF_MOBILE = "mobile"
        const val PREF_EMAIL = "email"
        const val PREF_TOKEN = "token"

        @JvmStatic
        fun instance() = Holder.instance

        /** 已注册的用户敏感的缓存 */
        private val registeredCaches = ArrayList<LogoutCallback>()

        /** 注册用户敏感的缓存信息 */
        fun registerUserSensitiveCache(logoutCallback: LogoutCallback) {
            registeredCaches.add(logoutCallback)
        }
    }

    private object Holder {
        @JvmStatic
        val instance: PassportRepository = PassportRepository()

    }
}