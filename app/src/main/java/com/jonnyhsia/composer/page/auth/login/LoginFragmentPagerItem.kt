package com.jonnyhsia.composer.page.auth.login

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.jonnyhsia.composer.page.auth.register.RegisterFragment
import com.jonnyhsia.composer.page.auth.register.RegisterPresenter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem

/**
 * @author JonnyHsia on 18/1/1.
 */
class LoginFragmentPagerItem
    : FragmentPagerItem("登录", 1f, RegisterFragment::class.java.name, Bundle()) {

    override fun instantiate(context: Context?, position: Int): Fragment {
        val instance = super.instantiate(context, position)

        if (instance is RegisterFragment) {
            RegisterPresenter(instance)
        }

        return instance
    }
}
