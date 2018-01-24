package com.jonnyhsia.composer.page.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.SimpleClick
import com.jonnyhsia.composer.kit.context
import com.jonnyhsia.composer.kit.find
import com.jonnyhsia.composer.kit.tint
import com.jonnyhsia.highlighttoolbar.HighlightToolbar
import me.drakeet.multitype.ItemViewBinder

class PageHeaderViewBinder(
        private val onClickIcon: SimpleClick,
        private val onClickSearchBar: SimpleClick
) : ItemViewBinder<PageHeader, PageHeaderViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_page_header, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, pageHeader: PageHeader) {
        with(holder.toolbar) {
            this ?: return@with
            title = pageHeader.title
            subTitle = pageHeader.subTitle
            setIconClickListner(onClickIcon)
        }

        holder.find<EditText>(R.id.searchBar)?.run {
            compoundDrawablesRelative[0]?.tint(
                    holder.context.resources.getColor(R.color.textDisable))
            setOnClickListener(onClickSearchBar)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val toolbar: HighlightToolbar? = find(R.id.toolbar)
    }
}
