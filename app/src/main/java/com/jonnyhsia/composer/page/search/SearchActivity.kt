package com.jonnyhsia.composer.page.search

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.replaceFragment
import com.jonnyhsia.composer.kit.setWindowBackground
import com.jonnyhsia.composer.page.base.DayNightActivity

class SearchActivity : DayNightActivity() {

    override fun getContentLayoutRes() = R.layout.activity_common

    override fun onContentViewCreated(savedInstanceState: Bundle?) {
        setWindowBackground(ColorDrawable(Color.WHITE))

        val fragment = SearchFragment().also { SearchPresenter(it) }
        replaceFragment(R.id.container, fragment, "search_fragment")
    }

}