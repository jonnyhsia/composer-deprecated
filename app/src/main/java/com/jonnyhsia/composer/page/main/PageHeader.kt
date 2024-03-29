package com.jonnyhsia.composer.page.main

import android.support.annotation.DrawableRes

data class PageHeader(
        val title: String,
        val subTitle: String,
        @DrawableRes val iconRes: Int? = null,
        val iconUrl: String? = null
)