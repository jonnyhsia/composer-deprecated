package com.jonnyhsia.composer.biz.home.entity

import com.jonnyhsia.composer.biz.story.entity.Story

data class FollowingUserStory(
        val stories: List<Story>
)