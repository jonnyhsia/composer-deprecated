package com.jonnyhsia.composer.page.main.timeline

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.app.Models
import com.jonnyhsia.composer.biz.home.entity.HomeChannel
import com.jonnyhsia.composer.biz.home.entity.TopStories
import com.jonnyhsia.composer.kit.toast
import com.jonnyhsia.composer.page.base.BaseFragment
import com.jonnyhsia.composer.page.main.PageHeader
import com.jonnyhsia.composer.page.main.PageHeaderViewBinder
import com.jonnyhsia.composer.page.main.timeline.multitype.HomeChannelViewBinder
import com.jonnyhsia.composer.page.main.timeline.multitype.TopStoryViewBinder
import com.jonnyhsia.composer.router.Router
import com.jonnyhsia.composer.ui.DividerDecoration
import com.jonnyhsia.composer.ui.Scroll2Top
import kotlinx.android.synthetic.main.fragment_timeline.recyclerTimeline
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter

/**
 * @author JonnyHsia on 18/1/5.
 */
class TimelineFragment : BaseFragment<TimelineContract.Presenter>(), TimelineContract.View, Scroll2Top {

    private var adapter = MultiTypeAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun initialize(timelineModels: Models) {
        registerTimelineAdapter()

        val dividerDrawable = resources.getDrawable(R.drawable.divider_timeline)
        val itemDecoration = DividerDecoration(
                dividerByPosition = {
                    if (timelineModels[it] !is PageHeader && it < timelineModels.size - 1) {
                        dividerDrawable
                    } else {
                        null
                    }
                })

        recyclerTimeline.setHasFixedSize(true)
        recyclerTimeline.addItemDecoration(itemDecoration)
        recyclerTimeline.layoutManager = LinearLayoutManager(activity)
        recyclerTimeline.adapter = adapter

        adapter.items = timelineModels
        adapter.notifyDataSetChanged()
    }

    private fun registerTimelineAdapter() {
        adapter.register(PageHeader::class.java, PageHeaderViewBinder(
                onClickIcon = {
                    toast("Hello")
                },
                onClickSearchBar = {
                    router("page://${Router.URI_SEARCH}")
                            .withBundle(ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    activity!!, it, getString(R.string.transition_search_bar)).toBundle())
                            .navigate()
                }
        ))
        adapter.register(TopStories::class.java, TopStoryViewBinder(
                onClickStory = {
                    toast("故事")
                },
                onStoryCollect = {
                    toast("收藏")
                }
        ))
        adapter.register(HomeChannel::class.java, HomeChannelViewBinder(
                onClick = {
                    toast("查看更多")
                },
                onStoryClick = {
                    toast("故事")
                },
                onStoryCollect = {
                    toast("收藏")
                }))
    }

    override fun showTimeline(timelineData: Items) {
        adapter.items = timelineData
        // adapter.notifyDataSetChanged()
    }

    override fun scroll() {
        recyclerTimeline.smoothScrollToPosition(0)
    }
}