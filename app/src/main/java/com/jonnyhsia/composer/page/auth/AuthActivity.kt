package com.jonnyhsia.composer.page.auth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.kit.hideKeyboard
import com.jonnyhsia.composer.kit.navigate
import com.jonnyhsia.composer.kit.setWindowBackground
import com.jonnyhsia.composer.page.auth.login.LoginContract
import com.jonnyhsia.composer.page.auth.login.LoginFragment
import com.jonnyhsia.composer.page.auth.login.LoginPresenter
import com.jonnyhsia.composer.page.auth.register.RegisterContract
import com.jonnyhsia.composer.page.auth.register.RegisterFragment
import com.jonnyhsia.composer.page.auth.register.RegisterPresenter
import com.jonnyhsia.composer.router.Router
import com.jonnyhsia.composer.ui.NeatToolbar
import kotlinx.android.synthetic.main.activity_auth.btnLoginOrRegister
import kotlinx.android.synthetic.main.activity_auth.tabLayout
import kotlinx.android.synthetic.main.activity_auth.toolbar
import kotlinx.android.synthetic.main.activity_auth.tvForgetPassword
import kotlinx.android.synthetic.main.activity_auth.viewPager

class AuthActivity : AppCompatActivity() {

    private var skipTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowBackground(ColorDrawable(Color.WHITE))
        setContentView(R.layout.activity_auth)

        initSkipCountDown()

        val adapter = AuthPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        btnLoginOrRegister.setOnClickListener {
            adapter.buttonClicked(viewPager.currentItem)
        }

        tvForgetPassword.setOnClickListener {
            navigate("native://${Router.URI_FORGET_PASSWORD}")
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                hideKeyboard()
            }

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        tvForgetPassword.text = ""
                        tvForgetPassword.visibility = View.GONE
                        toolbar.setMiddleText("加入创作")
                        btnLoginOrRegister.text = getString(R.string.action_register)
                    }
                    1 -> {
                        tvForgetPassword.text = "忘记密码"
                        tvForgetPassword.visibility = View.VISIBLE
                        toolbar.setMiddleText("欢迎回来")
                        btnLoginOrRegister.text = getString(R.string.action_login)
                    }
                    else -> throw IllegalStateException("Unexpected position of viewPager")
                }
            }
        })

        skipTimer?.start()
    }

    private fun initSkipCountDown() {
        val actionSkip = NeatToolbar.Action(
                actionText = "跳过",
                initialEnable = false,
                onAction = {
                    // 标记为已经过登录/注册/跳过
                    Repository.getConfigRepository().markHavePassedAuthPage()
                    navigate("native://${Router.URI_MAIN}")
                    finish()
                })
        toolbar.inflateActions(actionSkip)

        skipTimer = object : CountDownTimer(9000, 1000) {
            override fun onFinish() {
                actionSkip.actionView?.isEnabled = true
                (actionSkip.actionView as? TextView)?.text = "跳过"
            }

            override fun onTick(millisUntilFinished: Long) {
                (actionSkip.actionView as? TextView)?.text =
                        getString(R.string.action_skip_auth_countdown, millisUntilFinished / 1000)
            }
        }
    }

    class AuthPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        fun buttonClicked(pos: Int) {
            val view = getItem(pos)
            when (view) {
                is LoginContract.View -> view.loginAction()
                is RegisterContract.View -> view.registerAction()
            }
        }

        private val fragments = arrayOf(
                RegisterFragment().also { RegisterPresenter(it) },
                LoginFragment().also { LoginPresenter(it) }
        )

        private val titles = arrayOf("注册账号", "登录账号")

        override fun getItem(position: Int) = fragments[position]

        override fun getCount() = fragments.size

        override fun getPageTitle(position: Int) = titles[position]
    }

    override fun onDestroy() {
        super.onDestroy()
        skipTimer?.cancel()
    }
}