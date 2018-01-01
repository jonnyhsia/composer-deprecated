package com.jonnyhsia.composer.biz.home

import com.jonnyhsia.composer.biz.profile.passport.PassportRepository
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeRepository : HomeDataSource {

    private var timelineCache: Any? = null

    init {
        PassportRepository.registerUserSensitiveCache { timelineCache = null }
    }

    override fun getTimelineData(observer: SingleObserver<Any>) {
        Single.create<Any> {
            Thread.sleep(0)
            val data = "hello"
            timelineCache = data
            it.onSuccess(data)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance = HomeRepository()
    }
}