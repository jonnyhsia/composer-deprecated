package com.jonnyhsia.composer.biz.profile

/**
 * 用户的基本信息
 */
data class User(
        val username: String,
        val nickname: String,
        val avatar: String,
        val mobile: String? = null,
        val email: String? = null,
        val token: String? = null,
        val userOverview: UserOverview? = null) {

    /**
     * 用户数据概览
     */
    data class UserOverview(
            val todayStory: Int,
            val totalStory: Int,
            val publicStory: Int,
            val friends: Int)
}