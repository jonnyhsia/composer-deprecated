package com.jonnyhsia.composer.page.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.addTextWatcher
import com.jonnyhsia.composer.kit.getColor
import com.jonnyhsia.composer.kit.tint
import com.jonnyhsia.composer.page.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_search.searchBar
import kotlinx.android.synthetic.main.fragment_search.tvCancel

class SearchFragment : BaseFragment<SearchContract.Presenter>(), SearchContract.View {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun render() {
        tvCancel.setOnClickListener { back() }
        searchBar.compoundDrawablesRelative[0]?.tint(resources.getColor(R.color.textDisable))
        searchBar.addTextWatcher({ _, editable ->
            presenter.keywordChanged(editable.toString())
        })
    }

    override fun back() {
        searchBar.setText("")
        super.back()
    }

    override fun changeSearchIconTint(tintColorRes: Int) {
        searchBar.compoundDrawablesRelative[0]?.tint(getColor(tintColorRes))
    }
}