package com.jonnyhsia.composer.biz.config

import com.jonnyhsia.composer.biz.base.BaseLogic
import com.jonnyhsia.composer.biz.base.Preference

@Suppress("ASSIGNED_BUT_NEVER_ACCESSED_VARIABLE", "UNUSED_VALUE")
class ConfigRepository : ConfigDataSource, BaseLogic() {

    override fun markNotFirstOpenApp() {
        var isFirstOpenApp by Preference(context ?: return, Preference.NAME_CONFIG, true)
        isFirstOpenApp = false
    }

    override fun isFirstOpenApp(): Boolean {
        val isFirstOpenApp by Preference(context ?: return true, Preference.NAME_CONFIG, true)
        return isFirstOpenApp
    }

    override fun markHavePassedAuthPage() {
        var havePassedAuthPage by Preference(context ?: return, Preference.NAME_CONFIG, false)
        havePassedAuthPage = true
    }

    override fun getAuthPageHavePassed(): Boolean {
        val havePassedAuthPage by Preference(context ?: return false, Preference.NAME_CONFIG, false)
        return havePassedAuthPage
    }

    companion object {
        @JvmStatic
        fun instance() = Holder.instance
    }

    private object Holder {
        @JvmStatic
        val instance = ConfigRepository()
    }
}