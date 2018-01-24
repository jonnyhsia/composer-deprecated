package com.jonnyhsia.composer.page.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.jonnyhsia.composer.kit.loge
import com.jonnyhsia.composer.kit.toast
import com.jonnyhsia.composer.router.Router
import kotlin.properties.Delegates

/**
 * @author JonnyHsia on 17/12/31.
 */
abstract class BaseFragment<T : BasePresenter> : Fragment(), BaseView<T> {

    var presenter by Delegates.notNull<T>()

    init {
        retainInstance = true
    }

    override fun bindPresenter(presenter: T) {
        this.presenter = presenter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.start()
    }

    override fun onResume() {
        super.onResume()
        presenter.resume()
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }

    override fun showMessage(message: String?, duration: Int) {
        if (message?.isNotBlank() == true) {
            toast(message, duration)
        }
    }

    override fun navigate(pageUriString: String) {
        Router.navigate(context, pageUriString)
    }

    override fun back() {
        activity?.onBackPressed()
    }

    override fun router(pageUriString: String) = Router.Builder(context, pageUriString)
}