package com.jonnyhsia.composer.page.base

import android.support.v7.app.AppCompatActivity
import com.jonnyhsia.composer.router.Router

/**
 * @author JonnyHsia on 18/1/5.
 */
abstract class BaseActivity : AppCompatActivity() {

    open var interceptorResult: Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        if (intent.getBooleanExtra("from_interceptor", false)) {
            Router.interceptorCallback?.callback(interceptorResult)
        }
    }
}