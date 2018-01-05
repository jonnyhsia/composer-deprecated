package com.jonnyhsia.composer.page.creativework

import android.os.Bundle
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.replaceFragment
import com.jonnyhsia.composer.page.base.DayNightActivity

class CreativeWorkActivity : DayNightActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)

        val fragment = CreativeWorkFragment().also { CreativeWorkPresenter() }
        replaceFragment(R.id.container, fragment, "create_story")
    }
}
