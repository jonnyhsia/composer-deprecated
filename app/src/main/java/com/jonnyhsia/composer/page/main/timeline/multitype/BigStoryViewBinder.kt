package com.jonnyhsia.composer.page.main.timeline.multitype

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.ItemClick

class BigStoryViewBinder(
        onStoryClick: ItemClick,
        onStoryCollect: ItemClick) : StoryViewBinder(onStoryClick, onStoryCollect) {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_big_story, parent, false)
        return StoryViewBinder.ViewHolder(root)
    }

}