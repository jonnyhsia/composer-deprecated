package com.jonnyhsia.composer.page.main.timeline

import com.jonnyhsia.composer.app.Models
import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.biz.home.HomeDataSource
import com.jonnyhsia.composer.biz.home.entity.HomeChannel
import com.jonnyhsia.composer.biz.home.entity.TimelineData
import com.jonnyhsia.composer.biz.home.entity.TopStories
import com.jonnyhsia.composer.biz.story.entity.Story
import com.jonnyhsia.composer.page.base.SimplePresenter
import com.jonnyhsia.composer.page.main.PageHeader
import me.drakeet.multitype.Items
import java.util.*

class TimelinePresenter(
        val view: TimelineContract.View
) : SimplePresenter(), TimelineContract.Presenter {

    private val homeDataSource: HomeDataSource
    private val timelineModels: Models

    init {
        view.bindPresenter(this)
        homeDataSource = Repository.getHomeRepository()
        timelineModels = Models()
    }

    override fun start() {
        timelineModels.clear()
        timelineModels.add(PageHeader(
                title = "故事时间线", subTitle = "01月19日, 18年", iconUrl = ""))
        view.initialize(timelineModels)
        loadTimelineData()
    }

    /**
     * 加载首页数据
     */
    private fun loadTimelineData() {
        homeDataSource.getTimelineData(
                onSubscribe = {
                    disposable.add(it)
                },
                getTimelineDataSuccess = {
                    view.showTimeline(assembleTimelineData(it))
                },
                onFailed = {
                    view.showMessage(it)
                },
                onFinally = {
                }
        )
    }

    private fun assembleTimelineData(timelineData: TimelineData): Items {

        timelineModels.add(TopStories(
                stories = Arrays.asList(Story.sample(), Story.sample2(), Story.sample3()),
                title = "头条故事",
                date = System.currentTimeMillis()
        ))

        for (i in 0..4) {
            timelineModels.add(HomeChannel(
                    channel = HomeChannel.CHANNEL_TOPIC,
                    title = "Channel",
                    stories = Arrays.asList(Story.sample(), Story.sample2(), Story.sample3())
            ))
        }


        //        val topStories = timelineData.topStories
        //        val followingUserStories = timelineData.followingUserStories
        //        val topicStoriesList = timelineData.topicStoriesList
        //        val guessYouLikeStories = timelineData.guessYouLikeStories
        //
        //        timelineModels.add(topStories)
        //
        //        // 如果 Topic 有很多的话
        //        if (topicStoriesList != null && topicStoriesList.topics.size > 2) {
        //            topicStoriesList.topics.forEachIndexed { index, topicStory ->
        //                when (index) {
        //                    1 -> timelineModels.add(followingUserStories)
        //                    2 -> timelineModels.add(guessYouLikeStories)
        //                }
        //                timelineModels.add(topicStory)
        //            }
        //            return timelineModels
        //        }
        //
        //        timelineModels.add(followingUserStories)
        //
        //        if (topicStoriesList != null) {
        //            when (topicStoriesList.topics.size) {
        //                0 -> {
        //                    timelineModels.add(guessYouLikeStories)
        //                }
        //                1 -> {
        //                    timelineModels.add(topicStoriesList.topics[0])
        //                    timelineModels.add(guessYouLikeStories)
        //                }
        //                2 -> {
        //                    timelineModels.add(topicStoriesList.topics[0])
        //                    timelineModels.add(guessYouLikeStories)
        //                    timelineModels.add(topicStoriesList.topics[1])
        //                }
        //            }
        //        }

        return timelineModels
    }


}