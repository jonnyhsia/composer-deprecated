package com.jonnyhsia.composer.page.splash

import android.os.CountDownTimer
import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.kit.loge
import com.jonnyhsia.composer.page.base.SimplePresenter
import com.jonnyhsia.composer.router.Router
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

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
        view.startAnimating()
        timer.start()

        Repository.getHomeRepository().getTimelineData(object : SingleObserver<Any> {
            override fun onSubscribe(d: Disposable) {
                disposable.add(d)
            }

            override fun onError(e: Throwable) {
                loge(e.message)
            }

            override fun onSuccess(t: Any) {
                // 根据登录情况跳转页面
                // TODO 未登录也能跳转到主页面
                view.navigate(if (isAuthPagePassed) {
                    "native://${Router.URI_MAIN}"
                } else {
                    "native://${Router.URI_AUTH}"
                })
                view.back()
            }
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