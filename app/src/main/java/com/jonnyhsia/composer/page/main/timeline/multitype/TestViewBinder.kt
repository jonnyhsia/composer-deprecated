package com.jonnyhsia.composer.page.main.timeline.multitype

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jonnyhsia.composer.R

import me.drakeet.multitype.ItemViewBinder

class TestViewBinder : ItemViewBinder<TestViewBinder.Test, TestViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_test, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, test: TestViewBinder.Test) {

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class Test
}
