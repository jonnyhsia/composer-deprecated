package com.jonnyhsia.composer.page.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.page.base.BaseFragment

/**
 * @author JonnyHsia on 18/1/1.
 */
class RegisterFragment : BaseFragment<RegisterContract.Presenter>(), RegisterContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun render() {

    }
}