package com.jonnyhsia.composer.page.splash


import android.animation.ObjectAnimator
import android.animation.ValueAnimator.REVERSE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator

import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.app.GlideApp
import com.jonnyhsia.composer.kit.multiClick
import com.jonnyhsia.composer.page.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_splash.imgSplashIllustration
import kotlinx.android.synthetic.main.fragment_splash.imgSplashText
import kotlinx.android.synthetic.main.fragment_splash.progress


class SplashFragment : BaseFragment<SplashContract.Presenter>(), SplashContract.View {

    private val bonusScene: (View) -> Unit = {
        presenter.triggerBonusScene()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun render() {
        GlideApp.with(this)
                .load(R.mipmap.img_splash_text)
                .into(imgSplashText)

        GlideApp.with(this)
                .load(R.mipmap.img_splash_illustration)
                .into(imgSplashIllustration)

        // 短时间内点击 5 次图片触发彩蛋
        imgSplashIllustration.multiClick(
                multiClicked = { presenter.triggerBonusScene() },
                clickTimes = 5,
                timeLimit = 2000)
    }

    override fun showLoadingIndicator() {
        progress.visibility = View.VISIBLE
    }

    override fun startAnimating() {
        val alphaAnim = ObjectAnimator.ofFloat(imgSplashIllustration, "alpha", 1f, 0.38f)
        alphaAnim.repeatCount = -1
        alphaAnim.repeatMode = REVERSE
        alphaAnim.duration = 1200
        alphaAnim.interpolator = AccelerateDecelerateInterpolator()
        alphaAnim.start()
    }

}