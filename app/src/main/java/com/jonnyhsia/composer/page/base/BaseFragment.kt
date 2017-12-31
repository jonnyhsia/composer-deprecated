package com.jonnyhsia.composer.page.base

import android.os.Bundle
import android.support.v4.app.Fragment
import kotlin.properties.Delegates

/**
 * @author JonnyHsia on 17/12/31.
 */
abstract class BaseFragment<T : BasePresenter> : Fragment(), BaseView<T> {

    var presenter by Delegates.notNull<T>()

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

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.destroy()
    }

    override fun navigate(pageUriString: String) {

    }
}