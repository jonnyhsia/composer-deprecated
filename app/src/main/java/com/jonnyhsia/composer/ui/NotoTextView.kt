package com.jonnyhsia.composer.ui

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import com.jonnyhsia.composer.app.App

/**
 * @author JonnyHsia on 18/1/1.
 */
class NotoTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        initAttrs(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initAttrs(context, attrs)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        typeface = App.TYPEFACE
        includeFontPadding = false
    }
}