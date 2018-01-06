package com.jonnyhsia.composer.biz.home

import com.jonnyhsia.composer.biz.home.entity.TopStories
import com.jonnyhsia.composer.app.AppError
import com.jonnyhsia.composer.biz.base.BaseLogic.Companion.getUsername
import com.jonnyhsia.composer.biz.base.BaseLogic.Companion.retrofit
import com.jonnyhsia.composer.biz.base.OnFailed
import com.jonnyhsia.composer.biz.base.OnFinally
import com.jonnyhsia.composer.biz.base.OnSubscribe
import com.jonnyhsia.composer.biz.base.Response
import com.jonnyhsia.composer.biz.base.RxHttpHandler
import com.jonnyhsia.composer.biz.base.RxHttpSchedulers
import com.jonnyhsia.composer.biz.home.entity.FollowingUserStory
import com.jonnyhsia.composer.biz.home.entity.RecommendedStories
import com.jonnyhsia.composer.biz.home.entity.TimelineData
import com.jonnyhsia.composer.biz.home.entity.TopicStories
import com.jonnyhsia.composer.biz.profile.passport.PassportRepository
import com.jonnyhsia.composer.biz.story.StoryApi
import com.jonnyhsia.composer.kit.handle
import com.jonnyhsia.composer.kit.loge
import io.reactivex.Observable
import io.reactivex.Single

class HomeRepository : HomeDataSource {

    private var timelineCache: Any? = null

    private val storyApi = retrofit.create(StoryApi::class.java)

    init {
        PassportRepository.registerUserSensitiveCache { timelineCache = null }
    }

    override fun getTimelineData(
            onSubscribe: OnSubscribe,
            getTimelineDataSuccess: GetTimelineDataSuccess,
            onFailed: OnFailed,
            onFinally: OnFinally) {

        val sources = ArrayList<Single<*>>()
        val username = getUsername()

        sources.add(storyApi.getTopStories().handle())
        sources.add(storyApi.getGuessLike(username ?: "").handle())

        if (username != null) {
            sources.add(storyApi.getUserFollowingTopics(username).handle())
            sources.add(storyApi.getUserTimeline(username).handle())
        }

        Single.zip(sources, { responseFromSources: Array<out Any> ->
            val timeline = TimelineData()
            responseFromSources.forEach {
                when (it) {
                    is TopStories -> timeline.topStories = it
                    is TopicStories -> timeline.topicStoriesList = it
                    is FollowingUserStory -> timeline.followingUserStories = it
                    is RecommendedStories -> timeline.guessYouLikeStories = it
                    else -> loge("首页数据出问题了?")
                }
            }
            return@zip timeline
        }).compose(RxHttpSchedulers.composeSingle())
                .doOnSubscribe(onSubscribe)
                .doFinally(onFinally)
                .subscribe({
                    getTimelineDataSuccess(it)
                }, { e ->
                    if (e is AppError.AppException) {
                        onFailed(e.message)
                    } else {
                        onFailed(e.message)
                    }
                })
    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance = HomeRepository()
    }
}