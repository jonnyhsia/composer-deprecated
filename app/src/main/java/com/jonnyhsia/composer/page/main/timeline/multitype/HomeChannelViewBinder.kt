package com.jonnyhsia.composer.page.main.timeline.multitype

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.biz.home.entity.HomeChannel
import com.jonnyhsia.composer.biz.story.entity.Story
import com.jonnyhsia.composer.kit.ItemClick
import com.jonnyhsia.composer.kit.SimpleClick
import com.jonnyhsia.composer.kit.context
import com.jonnyhsia.composer.kit.find
import com.jonnyhsia.composer.kit.setOnClickListener
import com.jonnyhsia.composer.kit.setText
import me.drakeet.multitype.ItemViewBinder
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

class HomeChannelViewBinder(private val onClick: SimpleClick,
                            private val onStoryClick: ItemClick,
                            private val onStoryCollect: ItemClick
) : ItemViewBinder<HomeChannel, HomeChannelViewBinder.ViewHolder>() {

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_home_channel, parent, false)
        return ViewHolder(root)
    }

    override fun onBindViewHolder(holder: ViewHolder, homeChannel: HomeChannel) {
        holder.setText(R.id.tvChannelTitle, "# ${homeChannel.title}")
        holder.setOnClickListener(R.id.tvSeeMore, onClick)

        val storyAdapter = MultiTypeAdapter()
        storyAdapter.register(Story::class.java, StoryViewBinder(onStoryClick, onStoryCollect))
        storyAdapter.items = Items(homeChannel.stories)

        with(holder.recyclerTopStory) {
            this ?: return@with
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(holder.context)
            adapter = storyAdapter
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val recyclerTopStory: RecyclerView? = find(R.id.recyclerChannel)
    }
}
