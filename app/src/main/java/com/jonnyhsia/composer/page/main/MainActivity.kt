package com.jonnyhsia.composer.page.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.addOrShowFragment
import com.jonnyhsia.composer.kit.navigate
import com.jonnyhsia.composer.kit.setWindowBackground
import com.jonnyhsia.composer.page.base.DayNightActivity
import com.jonnyhsia.composer.page.main.discover.DiscoverFragment
import com.jonnyhsia.composer.page.main.inbox.InboxFragment
import com.jonnyhsia.composer.page.main.inbox.InboxPresenter
import com.jonnyhsia.composer.page.main.me.MeFragment
import com.jonnyhsia.composer.page.main.timeline.TimelineFragment
import com.jonnyhsia.composer.page.main.timeline.TimelinePresenter
import com.jonnyhsia.composer.router.Router
import com.jonnyhsia.composer.ui.Scroll2Top
import com.jonnyhsia.uilib.widget.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.bottomNavigation

class MainActivity : DayNightActivity() {

    override fun onContentViewCreated(savedInstanceState: Bundle?) {
        setWindowBackground(ColorDrawable(Color.WHITE))
        setUpBottomNavigation()
    }

    override fun getContentLayoutRes() = R.layout.activity_main

    /** 设置底部导航栏 */
    private fun setUpBottomNavigation() {
        val navItems = arrayListOf(
                BottomNavigation.BottomNavItem(R.mipmap.ic_nav_timeline),
                BottomNavigation.BottomNavItem(R.mipmap.ic_nav_stories),
                BottomNavigation.BottomNavItem(R.mipmap.ic_nav_inbox),
                BottomNavigation.BottomNavItem(R.mipmap.ic_nav_me)
        )

        bottomNavigation.setNavItems(navItems)
                .addPrimarySelectListener {
                    navigate("page://${Router.URI_COMPOSE}")
                }
                .addItemSelectListener { oldPos, pos, _ ->
                    homePageNavigate(oldPos, pos)
                }
                .addItemReselectListener { pos, _ ->
                    // Repository.getPassportRepository().login(User(username = "supotato", avatar = "", nickname = ""))
                    (findFragmentByIndex(pos) as? Scroll2Top)?.scroll()
                }
                .performClickItem(0)
    }

    /**
     * 通过索引找到或构造对应 Fragment 的实例
     *
     * @param  pos Fragment 的索引
     * @return 返回当前显示的 Fragment
     */
    private fun findFragmentByIndex(pos: Int): Fragment {
        var fragment: Fragment? = supportFragmentManager.findFragmentByTag(generateFragmentTag(pos))

        if (fragment == null) {
            fragment = when (pos) {
                0 -> TimelineFragment().also { TimelinePresenter(it) }
                1 -> DiscoverFragment()
                2 -> InboxFragment().also { InboxPresenter(it) }
                3 -> MeFragment()
                else -> throw IllegalArgumentException("Unexpected index of fragment.")
            }
        }

        return fragment
    }

    /**
     * 首页  Fragment 导航
     *
     * @param oldPos 之前停留的页面索引
     * @param pos    导航的页面索引
     */
    private fun homePageNavigate(oldPos: Int, pos: Int) {
        val destination = findFragmentByIndex(pos)
        val xaction = supportFragmentManager.beginTransaction()
        xaction.setCustomAnimations(R.anim.popup_enter, R.anim.popup_exit)

        if (oldPos != -1) {
            val oldFragment = findFragmentByIndex(oldPos)
            xaction.hide(oldFragment)
        }

        xaction.addOrShowFragment(R.id.container, destination, generateFragmentTag(pos))
        xaction.commit()
    }

    private fun generateFragmentTag(pos: Int) = "fragment_$pos"
}
