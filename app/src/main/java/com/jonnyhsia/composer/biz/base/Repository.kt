package com.jonnyhsia.composer.biz.base

import android.content.Context
import com.jonnyhsia.composer.BuildConfig
import com.jonnyhsia.composer.biz.config.ConfigRepository
import com.jonnyhsia.composer.biz.home.HomeRepository
import com.jonnyhsia.composer.biz.profile.ProfileRepository
import com.jonnyhsia.composer.biz.profile.passport.PassportRepository

/**
 * @author JonnyHsia on 17/12/31.
 */
object Repository {

    fun initialize(context: Context) {
        if (BaseLogic.isInitialized && BuildConfig.DEBUG) {
            throw IllegalStateException("请勿重复初始化 Base Logic.")
        }
        BaseLogic.initialize(context)
        preload()
    }

    fun preload() {
        if (!BaseLogic.isInitialized) {
            throw IllegalStateException("预加载前请先初始化 Base Logic.")
        }
    }

    fun getPassportRepository() = PassportRepository.instance()

    fun getProfileRepository() = ProfileRepository.instance()

    fun getHomeRepository() = HomeRepository.instance()

    fun getConfigRepository() = ConfigRepository.instance()
}