package com.jonnyhsia.composer.ui

import android.content.Context
import android.content.res.ColorStateList
import android.support.annotation.DrawableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.UIKit
import kotlin.properties.Delegates

/**
 * 简单整洁的 Toolbar
 * @author JonnyHsia on 18/1/1.
 */
class NeatToolbar : FrameLayout {
    private var tvTitle by Delegates.notNull<TextView>()

    private var tvLeftTitle by Delegates.notNull<TextView>()

    private var layoutActions by Delegates.notNull<LinearLayout>()

    /** 按钮高亮的颜色 */
    private var accentColor by Delegates.notNull<Int>()

    /** 按钮按下的颜色 */
    private var pressedColor by Delegates.notNull<Int>()

    /** 按钮处于禁用状态的颜色 */
    private var disabledColor by Delegates.notNull<Int>()

    /** 颜色状态 */
    private var colorStateList by Delegates.notNull<ColorStateList>()

    private val actions = ArrayList<Action>(MAX_ACTION_COUNT)

    var leftAction: () -> Unit by Delegates.notNull()

    constructor(context: Context) : super(context, null)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
        initView(context, attrs)
    }

    constructor(context: Context,
                attrs: AttributeSet?,
                defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    /** 初始化 */
    private fun initView(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NeatToolbar)

        val leftIcon = typedArray.getDrawable(R.styleable.NeatToolbar_leftIcon)
        val leftText = typedArray.getString(R.styleable.NeatToolbar_leftText)
        val title = typedArray.getString(R.styleable.NeatToolbar_middleTitle)
        accentColor = typedArray.getColor(R.styleable.NeatToolbar_accentColor, resources.getColor(R.color.textPrimary))
        pressedColor = typedArray.getColor(R.styleable.NeatToolbar_pressedColor, resources.getColor(R.color.textSecondary))
        disabledColor = typedArray.getColor(R.styleable.NeatToolbar_disabledColor, resources.getColor(R.color.textDisable))

        generateColorStateList()

        typedArray.recycle()

        LayoutInflater.from(context).inflate(R.layout.view_neat_toolbar, this, true)
        tvTitle = findViewById(R.id.tvTitle)
        tvLeftTitle = findViewById(R.id.btnLeft)
        layoutActions = findViewById(R.id.layoutActions)

        tvTitle.text = title

        tvLeftTitle.text = leftText
        tvLeftTitle.setTextColor(colorStateList)
        tvLeftTitle.setOnClickListener { leftAction() }

        if (leftIcon != null) {
            val iconSize = UIKit.dp2px(24).toInt()
            leftIcon.setBounds(0, 0, iconSize, iconSize)
            leftIcon.setTintList(colorStateList)
            tvLeftTitle.setCompoundDrawablesRelative(leftIcon, null, null, null)
        }
    }

    private fun generateColorStateList() {
        val colors = intArrayOf(pressedColor, pressedColor, accentColor, disabledColor, accentColor)
        val stats = arrayOf(
                intArrayOf(android.R.attr.state_focused, android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_window_focused),
                intArrayOf())
        colorStateList = ColorStateList(stats, colors)
    }

    fun inflateActions(actionList: List<Action>) {

        if (actionList.size > MAX_ACTION_COUNT) {
            actions.addAll(actionList.subList(0, MAX_ACTION_COUNT))
        } else {
            actions.addAll(actionList)
        }

        actions.forEach { action ->
            val actionView: View
            if (action.actionIcon == null) {
                actionView = TextView(context)
                actionView.setTextColor(colorStateList)
                actionView.text = action.actionText
            } else {
                actionView = ImageView(context)
                actionView.imageTintList = colorStateList
                actionView.setImageResource(action.actionIcon!!)
            }

            actionView.setOnClickListener { action.onAction() }
        }
    }

    fun setMiddleText(title: String) {
        tvTitle.text = title
    }

    data class Action(var actionText: String,
                      @DrawableRes var actionIcon: Int? = null) {
        var onAction: () -> Unit by Delegates.notNull()

    }
    companion object {
        private const val MAX_ACTION_COUNT = 3

    }
}
