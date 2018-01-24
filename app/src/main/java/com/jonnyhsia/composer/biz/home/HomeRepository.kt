package com.jonnyhsia.composer.biz.home

import com.jonnyhsia.composer.app.AppError
import com.jonnyhsia.composer.biz.base.BaseLogic.Companion.getUsername
import com.jonnyhsia.composer.biz.base.BaseLogic.Companion.retrofit
import com.jonnyhsia.composer.biz.base.OnFailed
import com.jonnyhsia.composer.biz.base.OnFinally
import com.jonnyhsia.composer.biz.base.OnSubscribe
import com.jonnyhsia.composer.biz.base.RxHttpSchedulers
import com.jonnyhsia.composer.biz.home.entity.FollowingUserStory
import com.jonnyhsia.composer.biz.home.entity.RecommendedStories
import com.jonnyhsia.composer.biz.home.entity.TimelineData
import com.jonnyhsia.composer.biz.home.entity.TopStories
import com.jonnyhsia.composer.biz.home.entity.TopicStories
import com.jonnyhsia.composer.biz.profile.passport.PassportRepository
import com.jonnyhsia.composer.biz.story.StoryApi
import com.jonnyhsia.composer.kit.Debug
import com.jonnyhsia.composer.kit.checkNotEmpty
import com.jonnyhsia.composer.kit.handle
import io.reactivex.Single

class HomeRepository : HomeDataSource {

    private var timelineCache: TimelineData? = null

    private val storyApi = retrofit.create(StoryApi::class.java)

    init {
        PassportRepository.registerUserSensitiveCache { timelineCache = null }
    }

    override fun getTimelineData(
            onSubscribe: OnSubscribe,
            getTimelineDataSuccess: GetTimelineDataSuccess,
            onFailed: OnFailed,
            onFinally: OnFinally) {

        // 若存在缓存数据则直接返回数据, 不需要再请求接口
        timelineCache?.let {
            getTimelineDataSuccess(it)
            onFinally()
            return
        }

        val sources = ArrayList<Single<*>>()
        val username = getUsername()

        sources.add(storyApi.getTopStories().handle())
        sources.add(storyApi.getGuessLike(username ?: "").handle())

        // 如果用户已登录, 还要调用户时间线与关注的话题文章
        if (username.checkNotEmpty()) {
            sources.add(storyApi.getUserFollowingTopics(username!!).handle())
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
                    else -> Debug.e("首页数据出问题了?")
                }
            }
            return@zip timeline
        }).compose(RxHttpSchedulers.composeSingle())
                .doOnSubscribe(onSubscribe)
                .doFinally(onFinally)
                .subscribe({
                    timelineCache = it
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