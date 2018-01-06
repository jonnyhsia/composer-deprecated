package com.jonnyhsia.composer.biz.home

import com.jonnyhsia.composer.biz.base.OnFailed
import com.jonnyhsia.composer.biz.base.OnFinally
import com.jonnyhsia.composer.biz.base.OnSubscribe
import com.jonnyhsia.composer.biz.home.entity.TimelineData

typealias GetTimelineDataSuccess = (TimelineData?) -> Unit

interface HomeDataSource {

    /**
     * 获取首页时间线数据
     */
    fun getTimelineData(
            onSubscribe: OnSubscribe,
            getTimelineDataSuccess: GetTimelineDataSuccess,
            onFailed: OnFailed,
            onFinally: OnFinally
    )
}