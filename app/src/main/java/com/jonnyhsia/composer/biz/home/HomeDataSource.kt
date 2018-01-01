package com.jonnyhsia.composer.biz.home

import io.reactivex.SingleObserver


interface HomeDataSource {

    /**
     * 获取首页时间线数据
     */
    fun getTimelineData(observer: SingleObserver<Any>)
}