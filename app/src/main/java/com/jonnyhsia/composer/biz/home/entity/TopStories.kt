package com.jonnyhsia.composer.biz.home.entity

import com.jonnyhsia.composer.biz.story.entity.Story

/**
 * 每日头条故事
 *
 * @property stories 头条故事
 * @property date    头条日期
 * @property title   标题(主题)
 */
data class TopStories(
        val stories: List<Story>,
        val date: Long,
        val title: String? = null
)
