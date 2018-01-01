package com.jonnyhsia.composer.page.splash

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.replaceFragment
import com.jonnyhsia.composer.kit.setWindowBackground

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowBackground(ColorDrawable(Color.parseColor("#FFFFFF")))
        setContentView(R.layout.activity_splash)

        val fragment = SplashFragment().also { SplashPresenter(it) }
        replaceFragment(R.id.container, fragment, "splash")

    }
}
