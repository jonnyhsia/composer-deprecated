package com.jonnyhsia.composer.app

/**
 * Created by JonnyHsia on 17/11/9.
 * 应用内异常枚举
 * TODO 换个好名字
 */
enum class AppError(val exception: AppException) {

    // Android 异常
    CONTEXT_UNSUITABLE(AppException(999, "当前上下文不适用于跳转页面")),
    INCORRECT_FRAGMENT_POS(AppException(998, "错误的 Fragment Position")),

    // 网络请求异常
    // REQUEST_FAILED(AppException(1, "服务器内部错误")),
    REQUEST_FAILED_UNEXPECTED(AppException(2, "网络异常")),
    UNAVAILABLE_NETWORK(AppException(10, "网络不可用")),

    // 登录注册, 用户相关异常
    NO_USER_LOGIN_INFO(AppException(101, "本地没有保存的用户登录信息")),

    PASSWORD_NOT_MATCH_USERNAME(AppException(1003, "用户名与密码不匹配")),
    // 请求异常
    INCORRECT_REMOTE_REQUEST(AppException(151, "分发错误的远程请求")),
    INCORRECT_LOCAL_REQUEST(AppException(152, "分发错误的本地请求")),

    // 路由异常
    ROUTER_INVALID_PAGE_SCHEME(AppException(200, "非法的页面")),

    ROUTER_MISMATCHED_SCHEME(AppException(201, "页面协议不匹配"));

    fun code() = exception.code

    companion object {
        /**
         * 通过 ErrorCode 来查找对应的 AppError
         *
         * @param errorCode 错误代码
         */
        @Suppress("MemberVisibilityCanPrivate")
        fun codeOf(errorCode: Int): AppError? {
            return values().firstOrNull { it.exception.code == errorCode }
        }

        fun codeOfException(errorCode: Int): Exception? = codeOf(errorCode)?.exception
    }

    class AppException(
            val code: Int,
            message: String?,
            throwable: Throwable? = null
    ) : RuntimeException(message, throwable)
}