package com.jonnyhsia.composer.biz.home.entity

data class TimelineData(
        var topStories: TopStories? = null,
        var followingUserStories: FollowingUserStory? = null,
        var topicStoriesList: TopicStories? = null,
        var guessYouLikeStories: RecommendedStories? = null
)