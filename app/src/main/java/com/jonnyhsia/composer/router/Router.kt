package com.jonnyhsia.composer.router

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.ArrayMap
import com.jonnyhsia.composer.BuildConfig
import com.jonnyhsia.composer.biz.base.BaseLogic
import com.jonnyhsia.composer.biz.base.Repository
import com.jonnyhsia.composer.kit.Debug
import com.jonnyhsia.composer.kit.Disposable
import com.jonnyhsia.composer.kit.checkNotEmpty
import com.jonnyhsia.composer.page.auth.AuthActivity
import com.jonnyhsia.composer.page.creativework.CreativeWorkActivity
import com.jonnyhsia.composer.page.main.MainActivity
import com.jonnyhsia.composer.page.notfound.NotFoundActivity
import com.jonnyhsia.composer.page.search.SearchActivity
import java.lang.ref.WeakReference

/**
 * @author JonnyHsia on 18/1/1.
 */
@Suppress("MemberVisibilityCanPrivate", "UNUSED_PARAMETER")
object Router {

    private var disposableBundle by Disposable<Router, Bundle>(null)

    internal fun withBundle(bundle: Bundle) {
        disposableBundle = bundle
    }

    /**
     * 暴露给外部调用的跳转方法
     *
     * @param flag 原生页面跳转的 Intent Flag
     */
    internal fun navigate(context: Context?, pageUriString: String, flag: Int? = null) {
        if (context == null) {
            throw IllegalArgumentException("Context 为 null")
        }
        // 解析 Uri 字符串并根据协议跳转到不同类型的 Activity
        val uri = Uri.parse(pageUriString) ?: return

        if (uri.scheme == null || uri.host == null) {
            if (BuildConfig.DEBUG) {
                throw IllegalArgumentException("跳转的 URI 有误")
            } else {
                Debug.e("跳转的 URI 有误")
            }
            return
        }

        when (uri.scheme) {
            "page", "task" -> startActivityWithNativeUri(context, uri, flag)
            "http", "https" -> startActivityWithUrl(context, uri)
            else -> throw IllegalStateException("无法匹配对应的 Uri Scheme: $pageUriString")
        }
    }

    /**
     * 当参数较多较长时建议使用 Builder 的形式
     */
    class Builder(context: Context?, pageUriString: String) {
        private val uriBuilder: Uri.Builder = Uri.parse(pageUriString).buildUpon()
        private val contextReference = WeakReference(context)

        private var flag: Int? = null

        fun setNavigateFlag(flag: Int) {
            this.flag = flag
        }

        fun appendParam(key: String, value: String): Builder {
            uriBuilder.appendQueryParameter(key, value)
            return this
        }

        fun withBundle(bundle: Bundle?): Builder {
            disposableBundle = bundle
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
        val mapping = map[uri.host] ?: defaultMapping

        // 所有 mustLogin 为 true 的 mapping 都有一个登录的拦截器
        if (mapping.mustLogin && BaseLogic.getLoginUser() != null) {
            // startActivityWithNativeUri(context, Uri.parse("page://$URI_AUTH"))
            val authInterceptor = Mapping.Interceptor("page://$URI_AUTH", { true })
            execInterceptor(context, authInterceptor, uri, flag)
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

        execStartActivity(context, uri.scheme, mapping.target, args, mapping.requestCode, flag
                ?: mapping.flag)
    }

    /** 一次性的回调 */
    var interceptorCallback by Disposable<Router, InterceptorCallback>()

    private fun execInterceptor(context: Context, interceptor: Mapping.Interceptor, uri: Uri, flag: Int? = null) {
        val interceptorUri = Uri.parse(interceptor.destination)

        val mapping = map.getOrDefault(interceptorUri.host, defaultMapping)

        // 匹配参数键值对
        val args = Bundle()
        mapping.paramKeys?.forEach { key ->
            val value: String? = interceptorUri.getQueryParameter(key)
            if (value.checkNotEmpty()) {
                args.putString(key, value)
            }
        }

        val requestCode = mapping.requestCode
        val intent = Intent(context, mapping.target)

        intent.putExtras(args)

        interceptorCallback = InterceptorCallback { result ->
            if (result) {
                startActivityWithNativeUri(context, uri, flag)
            } else if (interceptor.negativeDestination != null) {
                startActivityWithNativeUri(context, Uri.parse(interceptor.negativeDestination))
            }
        }

        when {
            requestCode == null -> context.startActivity(intent, disposableBundle)
            context is Activity -> context.startActivityForResult(intent, requestCode, disposableBundle)
            context is Fragment -> context.startActivityForResult(intent, requestCode, disposableBundle)
            else -> throw IllegalArgumentException("传入了什么奇怪的 Context")
        }
    }

    private fun execStartActivity(context: Context,
                                  schema: String,
                                  target: Class<*>,
                                  args: Bundle,
                                  requestCode: Int?,
                                  flag: Int?) {
        val intent = Intent(context, target)
        intent.putExtras(args)

        flag?.let { intent.setFlags(it) }

        if ("task" == schema && requestCode != null) {
            when (context) {
                is Activity -> context.startActivityForResult(intent, requestCode, disposableBundle)
                is Fragment -> context.startActivityForResult(intent, requestCode, disposableBundle)
                else -> throw IllegalArgumentException("传入了什么奇怪的 Context")
            }
            return
        }

        // 如果 Context 既不是 Activity 也不是 Fragment
        // 直接进行跳转会抛出异常, 需要添加 Flag
        if (context !is Activity || context !is Fragment) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }

        context.startActivity(intent, disposableBundle)
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
        put(URI_COMPOSE, Mapping(target = CreativeWorkActivity::class.java,
                interceptor = Mapping.Interceptor("page://$URI_FEATURE?name=compose", {
                    Repository.getConfigRepository().getAuthPageHavePassed()
                })))
        put(URI_SEARCH, Mapping(target = SearchActivity::class.java))
        // put(URI_SHOWCASE, Mapping(target = ShowcaseActivity::class.java))
        // put(URI_AUTH, Mapping(target = AuthActivity::class.java))
        // put(URI_RECOMMENDED_STORY, Mapping(target = RecommendedStoryActivity::class.java))
    }

    ///////////////////////////////////////////////////////////////////////////
    // URI 常量 (便于定位是从哪些 Activity 跳转的)
    ///////////////////////////////////////////////////////////////////////////

    /** 介绍页 */
    const val URI_SHOWCASE = "Showcase"
    /** 登录注册页 */
    const val URI_AUTH = "Auth"
    /** 忘记密码 */
    const val URI_FORGET_PASSWORD = "ForgetPassword"

    /** 主页 */
    const val URI_MAIN = "Main"
    /** 搜索 */
    const val URI_SEARCH = "Search"
    /** 故事创作, 编辑页 */
    const val URI_COMPOSE = "Writing"
    /** 故事详情页 */
    const val URI_STORY_DETAIL = "StoryDetail"
    /** TODO: 公开故事详情页 */
    const val URI_PUBLIC_STORY_DETAIL = "StoryDetail"
    /** TODO: 用户列表 */
    const val URI_USER_LIST = "UserList"

    const val URI_FEATURE = "Feature"

    /** 推荐的故事 */
    const val URI_RECOMMENDED_STORY = "RecommendedStory"
}