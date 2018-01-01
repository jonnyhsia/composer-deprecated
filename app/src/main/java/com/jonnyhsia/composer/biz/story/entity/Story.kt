package com.jonnyhsia.composer.biz.story.entity

import java.util.Date

data class Story(val storyId: Long,
                 val authorId: Long,
                 val title: String,
                 val content: String,
                 val author: String,
                 val images: List<String>,
                 val createTime: Date)