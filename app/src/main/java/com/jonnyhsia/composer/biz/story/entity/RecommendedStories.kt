package com.jonnyhsia.composer.biz.story.entity

import com.jonnyhsia.composer.biz.profile.User
import java.util.Date

data class RecommendedStories(val title: String,
                              val banner: String,
                              val description: String,
                              val date: Date,
                              val stories: List<Story>,
                              val authors: List<User>)