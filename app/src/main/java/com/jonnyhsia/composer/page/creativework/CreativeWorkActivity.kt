package com.jonnyhsia.composer.page.creativework

import android.os.Bundle
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.replaceFragment
import com.jonnyhsia.composer.page.base.DayNightActivity

class CreativeWorkActivity : DayNightActivity() {

    override fun getContentLayoutRes() = R.layout.activity_common

    override fun onContentViewCreated(savedInstanceState: Bundle?) {
        val fragment = CreativeWorkFragment().also { CreativeWorkPresenter(it) }
        replaceFragment(R.id.container, fragment, "create_story")
    }

}
