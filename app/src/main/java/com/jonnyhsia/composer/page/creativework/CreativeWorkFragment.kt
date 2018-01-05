package com.jonnyhsia.composer.page.creativework

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.page.base.BaseFragment

/**
 * @author JonnyHsia on 18/1/5.
 */
class CreativeWorkFragment : BaseFragment<CreativeWorkContract.Presenter>(), CreativeWorkContract.View {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_creative_work, container, false)
    }
}