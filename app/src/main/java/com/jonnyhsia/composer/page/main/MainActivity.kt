package com.jonnyhsia.composer.page.main

import android.os.Bundle
import android.support.v4.app.Fragment
import com.jonnyhsia.composer.R
import com.jonnyhsia.composer.kit.addOrShowFragment
import com.jonnyhsia.composer.kit.navigate
import com.jonnyhsia.composer.page.base.DayNightActivity
import com.jonnyhsia.composer.page.main.inbox.InboxFragment
import com.jonnyhsia.composer.page.main.inbox.InboxPresenter
import com.jonnyhsia.composer.page.main.timeline.TimelineFragment
import com.jonnyhsia.composer.page.main.timeline.TimelinePresenter
import com.jonnyhsia.composer.router.Router
import com.jonnyhsia.uilib.widget.BottomNavigation
import kotlinx.android.synthetic.main.activity_main.bottomNavigation

class MainActivity : DayNightActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpBottomNavigation()
    }

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
                    navigate("native://${Router.URI_CREATE_STORY}")
                }
                .addItemSelectListener { oldPos, pos, _ ->
                    homePageNavigate(oldPos, pos)
                }
                .addItemReselectListener { pos, _ ->
                    findFragmentByIndex(pos)
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
                1 -> Fragment()
                2 -> InboxFragment().also { InboxPresenter(it) }
                3 -> Fragment()
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

        xaction.addOrShowFragment(R.id.container, destination, generateFragmentTag(pos))

        if (oldPos != -1) {
            val oldFragment = findFragmentByIndex(pos)
            xaction.hide(oldFragment)
        }

        xaction.commit()
    }

    private fun generateFragmentTag(pos: Int) = "fragment_$pos"
}
