package com.jonnyhsia.composer.page.splash

import android.os.CountDownTimer
import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.kit.logd
import com.jonnyhsia.composer.kit.loge
import com.jonnyhsia.composer.page.base.SimplePresenter
import com.jonnyhsia.composer.router.Router
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import kotlin.math.max

class SplashPresenter(
        val view: SplashContract.View
) : SimplePresenter(), SplashContract.Presenter {

    private val disposable = CompositeDisposable()

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
                    view.showMessage("O捷豹K")
                },
                onFailed = {
                    view.showMessage(it)
                },
                onFinally = {
                    enjoySplashScreen(requestTime = System.currentTimeMillis() - startTime)
                }
        )
    }

    /** 欣赏至少 3s 的动画 */
    private fun enjoySplashScreen(requestTime: Long) {
        disposable.add(Observable.timer(maxOf(0, 1500 - requestTime), TimeUnit.MILLISECONDS)
                .subscribe {
                    // 根据登录情况跳转页面
                    view.navigate(if (isAuthPagePassed) {
                        "native://${Router.URI_MAIN}"
                    } else {
                        "native://${Router.URI_AUTH}"
                    })
                    view.back()
                })
    }

    override fun triggerBonusScene() {
        // TODO: 彩蛋内容
        view.navigate("native://${Router.URI_AUTH}")
        view.back()
        view.showMessage("恭喜你 🎉 获得了成为会员的机会")
    }

    override fun pause() {
        timer.cancel()
    }

    override fun destroy() {
        super.destroy()
        disposable.dispose()
    }

    companion object {
        const val ACCEPTABLE_LOADING_TIME = 2500L
    }
}