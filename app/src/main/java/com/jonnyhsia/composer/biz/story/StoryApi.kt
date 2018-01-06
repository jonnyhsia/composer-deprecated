package com.jonnyhsia.composer.biz.story

import com.jonnyhsia.composer.biz.base.Response
import com.jonnyhsia.composer.biz.home.entity.FollowingUserStory
import com.jonnyhsia.composer.biz.home.entity.RecommendedStories
import com.jonnyhsia.composer.biz.home.entity.TopStories
import com.jonnyhsia.composer.biz.home.entity.TopicStories
import com.jonnyhsia.composer.biz.story.entity.Story
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author JonnyHsia on 17/12/31.
 */
interface StoryApi {

    @FormUrlEncoded
    @POST("story/top_story")
    fun getTopStories(
            @Field("date") date: Long = System.currentTimeMillis()
    ): Single<Response<TopStories>>

    /**
     * 获取用户时间线
     *
     * @param username 用户名/ID
     * @param offset   页数
     */
    @GET("story/{username}/timeline")
    fun getUserTimeline(@Path("username") username: String,
                        @Query("offset") offset: Int = 0,
                        @Query("limit") limit: Int = 20
    ): Single<Response<List<Story>>>

    @FormUrlEncoded
    @POST("story/{username}/following_topics")
    fun getUserFollowingTopics(
            @Path("username") username: String,
            @Field("limit") limit: Int = 10
    ): Single<Response<TopicStories>>

    @FormUrlEncoded
    @POST("story/{username}/following_user_story")
    fun getUserFollowingUserStory(
            @Path("username") username: String,
            @Field("limit") limit: Int = 10
    ): Single<Response<FollowingUserStory>>

    @FormUrlEncoded
    @POST("story/guess_like")
    fun getGuessLike(
            @Field("username") username: String
    ): Single<Response<RecommendedStories>>

}