package com.jonnyhsia.composer.app

import android.app.Application
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import com.jonnyhsia.composer.BuildConfig
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.biz.base.Repository
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates

/**
 * Application
 *
 * @author JonnyHsia on 17/12/31.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        INSTANCE = this

        // Model 的初始化与预加载
        Repository.initialize(this)

        initCrashReport()
    }

    /**
     * 初始化 Bugly 崩溃上报
     */
    private fun initCrashReport() {
        CrashReport.setIsDevelopmentDevice(applicationContext, BuildConfig.DEBUG)
        CrashReport.initCrashReport(applicationContext)
    }

    companion object {
        var INSTANCE: App by Delegates.notNull()
            private set

        /**
         * 字体
         */
        @JvmStatic
        val TYPEFACE: Typeface? by lazy {
            ResourcesCompat.getFont(INSTANCE, R.font.noto_sans_medium)
        }
    }
}