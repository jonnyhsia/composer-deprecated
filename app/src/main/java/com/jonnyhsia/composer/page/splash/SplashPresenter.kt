package com.jonnyhsia.composer.page.splash

import android.os.CountDownTimer
import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.page.base.SimplePresenter
import com.jonnyhsia.composer.router.Router
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class SplashPresenter(
        val view: SplashContract.View
) : SimplePresenter(), SplashContract.Presenter {

    /** 计时器, 用于后台数据时间 */
    private val timer: CountDownTimer

    /** 登录/注册页是否通过 */
    private var isAuthPagePassed = Repository.getConfigRepository().getAuthPageHavePassed()

    init {
        view.bindPresenter(this)
        timer = object : CountDownTimer(ACCEPTABLE_LOADING_TIME, ACCEPTABLE_LOADING_TIME) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                view.showLoadingIndicator()
            }
        }
    }

    override fun start() {
        view.render()
        execDataLoading()
    }

    /**
     * 预加载首页数据
     */
    private fun execDataLoading() {
        // 开始计时
        val startTime = System.currentTimeMillis()
        timer.start()
        view.startAnimating()

        Repository.getHomeRepository().getTimelineData(
                onSubscribe = { disposable.add(it) },
                getTimelineDataSuccess = {
                    view.showMessage("预加载完成")
                },
                onFailed = {
                    view.showMessage(it)
                },
                onFinally = {
                    enjoySplashAnim(requestTime = System.currentTimeMillis() - startTime)
                }
        )
    }

    /** 至少欣赏 2s 的动画 */
    private fun enjoySplashAnim(requestTime: Long) {
        disposable.add(Observable.timer(maxOf(0, 2000 - requestTime), TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    view.navigate(if (isAuthPagePassed) {
                        "page://${Router.URI_MAIN}"
                    } else {
                        "page://${Router.URI_AUTH}"
                    })
                    view.back()
                })
    }

    override fun triggerBonusScene() {
        // TODO: 彩蛋内容
        view.navigate("page://${Router.URI_AUTH}")
        view.back()
        view.showMessage("🎉 当当~就是这么简陋的彩蛋~")
    }

    override fun pause() {
        timer.cancel()
    }

    companion object {
        const val ACCEPTABLE_LOADING_TIME = 2500L
    }
}