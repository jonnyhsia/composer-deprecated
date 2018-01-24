package com.jonnyhsia.composer.page.base

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.LayoutRes
import com.githang.statusbar.StatusBarCompat

/**
 * @author JonnyHsia on 18/1/5.
 */
abstract class DayNightActivity : BaseActivity() {

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentLayoutRes())
        // TODO: 日夜间模式支持
        StatusBarCompat.setStatusBarColor(this, Color.WHITE, true)

        onContentViewCreated(savedInstanceState)
    }

    @LayoutRes
    abstract fun getContentLayoutRes(): Int

    abstract fun onContentViewCreated(savedInstanceState: Bundle?)
}