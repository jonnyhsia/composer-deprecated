package com.jonnyhsia.composer.biz.home.entity

import com.jonnyhsia.composer.biz.story.entity.Story

/**
 * Created by JonnyHsia on 18/1/6.
 */
data class UserStories(
        val username: String,
        val stories: List<Story>
)