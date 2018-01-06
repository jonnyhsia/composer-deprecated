package com.jonnyhsia.composer.biz.home.entity

import com.jonnyhsia.composer.biz.story.entity.Story

/**
 * Created by JonnyHsia on 18/1/5.
 * Description
 */
data class RecommendedStories(
        val stories: List<Story>,
        val reason: String,
        val title: String = "",
        val coverImage: String = ""
)