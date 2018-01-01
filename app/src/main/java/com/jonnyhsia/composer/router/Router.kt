package com.jonnyhsia.composer.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.ArrayMap
import com.jonnyhsia.composer.biz.BaseLogic
import com.jonnyhsia.composer.kit.checkNotEmpty
import com.jonnyhsia.composer.page.auth.AuthActivity
import com.jonnyhsia.composer.page.main.MainActivity
import com.jonnyhsia.composer.page.notfound.NotFoundActivity
import java.lang.ref.WeakReference

/**
 * @author JonnyHsia on 18/1/1.
 */
@Suppress("MemberVisibilityCanPrivate", "UNUSED_PARAMETER")
object Router {

    /**
     * 暴露给外部调用的跳转方法
     *
     * @param flag 原生页面跳转的 Intent Flag
     */
    fun navigate(context: Context?, pageUriString: String, flag: Int? = null) {
        if (context == null) {
            throw IllegalArgumentException("Context 为 null")
        }
        // 解析 Uri 字符串并根据协议跳转到不同类型的 Activity
        val uri = Uri.parse(pageUriString) ?: return
        when (uri.scheme) {
            "native" -> startActivityWithNativeUri(context, uri, flag)
            "http", "https" -> startActivityWithUrl(context, uri)
            else -> throw IllegalStateException("无法匹配对应的 Uri Scheme: $pageUriString")
        }
    }

    /**
     * 当参数较多较长时建议使用 Builder 的形式
     */
    class Builder(context: Context?, pageUriString: String) {
        val uriBuilder: Uri.Builder = Uri.parse(pageUriString).buildUpon()
        val contextReference = WeakReference(context)

        var flag: Int? = null

        fun setNavigateFlag(flag: Int) {
            this.flag = flag
        }

        fun appendParam(key: String, value: String): Builder {
            uriBuilder.appendQueryParameter(key, value)
            return this
        }

        fun navigate() {
            Router.navigate(contextReference.get(), uriBuilder.build().toString(), flag)
        }
    }

    private fun startActivityWithUrl(context: Context, uri: Uri) {
        // TODO: 预留方法
    }

    /**
     * 通过 Native Uri 匹配对应的 Activity
     */
    private fun startActivityWithNativeUri(context: Context, uri: Uri, flag: Int? = null) {
        val mapping = map.getOrDefault(uri.host, defaultMapping)

        if (mapping.mustLogin && BaseLogic.getLoginUser() != null) {
            startActivityWithNativeUri(context, Uri.parse("native://$URI_AUTH"))
            return
        }

        // 匹配参数键值对
        val args = Bundle()
        mapping.paramKeys?.forEach { key ->
            val value: String? = uri.getQueryParameter(key)
            if (value.checkNotEmpty()) {
                args.putString(key, value)
            }
        }

        execStartActivity(context, mapping.target, args, mapping.requestCode, flag ?: mapping.flag)
    }

    private fun execStartActivity(context: Context,
                                  target: Class<*>,
                                  args: Bundle,
                                  requestCode: Int?,
                                  flag: Int?) {
        val intent = Intent(context, target)
        intent.putExtras(args)

        flag?.let { intent.setFlags(it) }

        when {
            requestCode == null -> context.startActivity(intent)
            context is Activity -> context.startActivityForResult(intent, requestCode)
            context is Fragment -> context.startActivityForResult(intent, requestCode)
            else -> throw IllegalArgumentException("传入了什么奇怪的 Context")
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // Map
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 默认跳转路由失败页
     */
    private val defaultMapping = Mapping(target = NotFoundActivity::class.java)

    private val map: Map<String, Mapping> = ArrayMap<String, Mapping>().apply {
        put(URI_MAIN, Mapping(target = MainActivity::class.java))
        put(URI_AUTH, Mapping(target = AuthActivity::class.java))
        // put(URI_SHOWCASE, Mapping(target = ShowcaseActivity::class.java))
        // put(URI_AUTH, Mapping(target = AuthActivity::class.java))
        // put(URI_RECOMMENDED_STORY, Mapping(target = RecommendedStoryActivity::class.java))
    }

    ///////////////////////////////////////////////////////////////////////////
    // URI 常量 (便于定位是从哪些 Activity 跳转的)
    ///////////////////////////////////////////////////////////////////////////

    /** 介绍页 */
    val URI_SHOWCASE = "Showcase"
    /** 登录注册页 */
    val URI_AUTH = "Auth"

    /** 主页 */
    val URI_MAIN = "Main"
    /** 故事创作, 编辑页 */
    val URI_CREATE_STORY = "Writing"
    /** 故事详情页 */
    val URI_STORY_DETAIL = "StoryDetail"
    /** TODO: 公开故事详情页 */
    val URI_PUBLIC_STORY_DETAIL = "StoryDetail"
    /** TODO: 用户列表 */
    val URI_USER_LIST = "UserList"

    /** 推荐的故事 */
    val URI_RECOMMENDED_STORY = "RecommendedStory"
}