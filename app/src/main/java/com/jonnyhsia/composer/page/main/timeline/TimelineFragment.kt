package com.jonnyhsia.composer.page.main.timeline

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.page.base.BaseFragment

/**
 * @author JonnyHsia on 18/1/5.
 */
class TimelineFragment : BaseFragment<TimelineContract.Presenter>(), TimelineContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }
}