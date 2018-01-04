package com.jonnyhsia.composer.biz.story

import com.jonnyhsia.composer.biz.base.Response
import com.jonnyhsia.composer.biz.story.entity.Story
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author JonnyHsia on 17/12/31.
 */
interface StoryApi {

    /**
     * 获取用户时间线
     *
     * @param username 用户名/ID
     * @param offset   页数
     */
    @GET("story/{username}/timeline")
    fun getUserTimeline(@Path("username") username: String,
                        @Query("offset") offset: Int = 0,
                        @Query("limit") limit: Int = 20)
            : Single<Response<List<Story>>>
}