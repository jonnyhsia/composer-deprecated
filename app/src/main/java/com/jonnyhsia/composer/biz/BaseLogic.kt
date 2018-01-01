package com.jonnyhsia.composer.biz

import android.content.Context
import android.util.Log
import com.jonnyhsia.composer.app.Const
import com.jonnyhsia.composer.biz.profile.User
import com.jonnyhsia.composer.biz.profile.passport.PassportRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates

/**
 * @author JonnyHsia on 17/12/31.
 */
open class BaseLogic {

    companion object {

        private const val READ_TIMEOUT = 10L
        private const val CONNECT_TIMEOUT = 10L

        /** BaseLogic 是否被初始化过了 */
        @JvmStatic
        var isInitialized = false

        /** Context 的弱引用 */
        private var contextReference: WeakReference<Context> by Delegates.notNull()

        val context: Context?
            get() = contextReference.get()

        var retrofit: Retrofit by Delegates.notNull()

        fun initialize(initialContext: Context) {
            contextReference = WeakReference(initialContext)

            val clientBuilder = OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(generateHttpLoggingInterceptor())

            retrofit = Retrofit.Builder()
                    .client(clientBuilder.build())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Const.BASE_URL)
                    .build()

            isInitialized = true
        }

        private fun generateHttpLoggingInterceptor() =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                    if (message.contains("{")) {
                        // 切割日志输出
                        val lineLength = 1000
                        for (i in 0..(message.length / lineLength)) {
                            val start = i * lineLength
                            val end = Math.min(message.length, (i + 1) * lineLength)
                            Log.d("JsonLog", message.substring(start, end))
                        }
                    } else {
                        Log.d("HttpLog", message)
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY)

        /**
         * @throws IllegalStateException 未初始化则抛出异常
         */
        private fun checkInitialize() {
            if (!isInitialized) throw IllegalStateException("BaseLogic 尚未初始化")
        }

        fun checkLogin(): Boolean {
            checkInitialize()
            return PassportRepository.instance().isLogin()
        }

        fun getLoginUser(): User? {
            checkInitialize()
            return PassportRepository.instance().getLoginUser()
        }

        fun getUserId(): String? {
            checkInitialize()
            return PassportRepository.instance().getUserId()
        }

    }
}