package com.jonnyhsia.composer.page.main.inbox

import com.jonnyhsia.composer.page.base.BasePresenter
import com.jonnyhsia.composer.page.base.BaseView

/**
 * @author JonnyHsia on 17/12/31.
 */
interface InboxContract {

    interface Presenter : BasePresenter {

    }

    interface View : BaseView<Presenter> {

        fun render()
    }
}