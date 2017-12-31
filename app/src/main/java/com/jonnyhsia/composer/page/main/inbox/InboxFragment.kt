package com.jonnyhsia.composer.page.main.inbox

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.page.base.BaseFragment

/**
 * 消息中心
 */
class InboxFragment : BaseFragment<InboxContract.Presenter>(), InboxContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }

    override fun render() {

    }

}
