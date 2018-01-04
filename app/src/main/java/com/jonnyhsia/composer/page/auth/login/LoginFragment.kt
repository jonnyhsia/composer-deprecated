package com.jonnyhsia.composer.page.auth.login

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.app.GlideApp
import com.jonnyhsia.composer.page.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.editPassword
import kotlinx.android.synthetic.main.fragment_login.editUsername
import kotlinx.android.synthetic.main.fragment_login.imgIllustration
import kotlinx.android.synthetic.main.fragment_login.tvPassword
import kotlinx.android.synthetic.main.fragment_login.tvUsername

/**
 * @author JonnyHsia on 18/1/1.
 */
class LoginFragment : BaseFragment<LoginContract.Presenter>(), LoginContract.View {

    private val alphaAnim: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(imgIllustration, "alpha", 1f, 0.38f).also {
            it.repeatCount = -1
            it.repeatMode = ValueAnimator.REVERSE
            it.duration = 1200
            it.interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun render() {
        GlideApp.with(this)
                .load(R.mipmap.img_illustration_paper_airplane)
                .into(imgIllustration)
    }

    override fun loginAction() {
        presenter.clickLogin(editUsername.text.toString(), editPassword.text.toString())
    }

    override fun showPasswordError() {
        tvUsername.toggleError()
        tvPassword.toggleError()
    }

    override fun showLoading() {
        alphaAnim.start()

        editUsername.isEnabled = false
        editPassword.isEnabled = false
    }

    override fun stopLoading() {
        alphaAnim.cancel()
        imgIllustration.alpha = 1f

        editUsername.isEnabled = true
        editPassword.isEnabled = true
    }
}