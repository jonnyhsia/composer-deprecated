package com.jonnyhsia.composer.biz.home.entity

import com.jonnyhsia.composer.biz.story.entity.Story

/**
 * Created by JonnyHsia on 18/1/5.
 * 主题
 */
data class TopicStories(
        val username: String,
        val topics: List<TopicStory>
) {

    data class TopicStory(val topic: String,
                          val stories: List<Story>
    )
}