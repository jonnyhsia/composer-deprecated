package com.jonnyhsia.composer.page.auth.register

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.Glide
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.page.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_register.editPassword
import kotlinx.android.synthetic.main.fragment_register.editUsername
import kotlinx.android.synthetic.main.fragment_register.imgIllustration

/**
 * @author JonnyHsia on 18/1/1.
 */
class RegisterFragment : BaseFragment<RegisterContract.Presenter>(), RegisterContract.View {

    private val alphaAnim: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(imgIllustration, "alpha", 1f, 0.38f).also {
            it.repeatCount = -1
            it.repeatMode = ValueAnimator.REVERSE
            it.duration = 1200
            it.interpolator = AccelerateDecelerateInterpolator()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun render() {
        Glide.with(this)
                .load(R.mipmap.img_illustration_write)
                .into(imgIllustration)
    }

    override fun registerAction() {
        presenter.clickRegister("", "", "")
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