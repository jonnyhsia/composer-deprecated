package com.jonnyhsia.composer.page.auth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.setWindowBackground
import com.jonnyhsia.composer.page.auth.login.LoginFragmentPagerItem
import com.jonnyhsia.composer.page.auth.register.RegisterFragmentPagerItem
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_auth.tabLayout
import kotlinx.android.synthetic.main.activity_auth.toolbar
import kotlinx.android.synthetic.main.activity_auth.viewPager

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowBackground(ColorDrawable(Color.WHITE))
        setContentView(R.layout.activity_auth)

        toolbar.leftAction = { onBackPressed() }


//        val pages = FragmentPagerItems.with(this)
//                .add("", FragmentPagerItemAdapter::class.java)
        val pages = FragmentPagerItems.with(this)
                .add(LoginFragmentPagerItem())
                .add(RegisterFragmentPagerItem())
                .create()

        viewPager.adapter = FragmentPagerItemAdapter(supportFragmentManager, pages)
        tabLayout.setViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                toolbar.setMiddleText(when (position) {
                    0 -> "加入创作"
                    1 -> "欢迎回来"
                    else -> throw IllegalStateException("Unexpected position of viewPager")
                })
            }
        })
    }
}