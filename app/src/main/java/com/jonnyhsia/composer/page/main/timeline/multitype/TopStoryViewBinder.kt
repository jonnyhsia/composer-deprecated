package com.jonnyhsia.composer.page.main.timeline.multitype

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.biz.home.entity.TopStories
import com.jonnyhsia.composer.biz.story.entity.Story
import com.jonnyhsia.composer.kit.ItemClick
import com.jonnyhsia.composer.kit.find
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

class TopStoryViewBinder(
        private val onClickStory: ItemClick,
        private val onStoryCollect: ItemClick
) : ItemViewBinder<TopStories, TopStoryViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_top_story, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, topStories: TopStories) {
        val storyAdapter = MultiTypeAdapter()
        storyAdapter.register(Story::class.java)
                .to(StoryViewBinder(onClickStory, onStoryCollect), BigStoryViewBinder(onClickStory, onStoryCollect))
                .withClassLinker { position, _ ->
                    if (position == 0) {
                        BigStoryViewBinder::class.java
                    } else {
                        StoryViewBinder::class.java
                    }
                }
        storyAdapter.items = Items(topStories.stories)

        with(holder.recyclerTopStory) {
            this ?: return@with
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = storyAdapter
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val recyclerTopStory: RecyclerView? = find(R.id.recyclerTopStory)
    }
}


