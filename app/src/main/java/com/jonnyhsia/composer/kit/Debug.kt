/*
 This is free and unencumbered software released into the public domain.
 Anyone is free to copy, modify, publish, use, compile, sell, or
 distribute this software, either in source code form or as a compiled
 binary, for any purpose, commercial or non-commercial, and by any
 means.
 For more information, please refer to <http://unlicense.org/>
 */
package com.jonnyhsia.composer.kit

import android.util.Log
import com.jonnyhsia.composer.BuildConfig

object Debug {

    /** Log输出所在类  */
    private var className: String? = null

    /** Log输出所在方法  */
    private var methodName: String? = null

    /** Log输出所行号  */
    private var lineNumber: Int = 0

    /** 是否处于 Debug 状态  */
    private val isDebuggable = BuildConfig.DEBUG

    /**
     * 创建Log输出的基本信息
     *
     * @param log 日志内容
     * @return 返回含有方法名与行号的日志
     */
    private fun createLog(log: Any): String {
        return "[" + methodName + "()" + " line:" + lineNumber + "] " + log.toString()
    }

    /**
     * 取得输出所在位置的信息 className methodName lineNumber
     *
     * @param stackTraceElements
     */
    private fun getMethodNames(stackTraceElements: Array<StackTraceElement>) {
        className = stackTraceElements[1].fileName.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
        methodName = stackTraceElements[1].methodName
        lineNumber = stackTraceElements[1].lineNumber
    }

    fun v(message: Any) {
        if (!isDebuggable) {
            return
        }

        getMethodNames(Throwable().stackTrace)
        Log.v(className, createLog(message))
    }

    fun d(message: Any) {
        if (!isDebuggable) {
            return
        }

        getMethodNames(Throwable().stackTrace)
        Log.d(className, createLog(message))
    }

    fun i(message: Any) {
        if (!isDebuggable) {
            return
        }

        getMethodNames(Throwable().stackTrace)
        Log.i(className, createLog(message))
    }

    fun w(message: Any) {
        if (!isDebuggable) {
            return
        }

        getMethodNames(Throwable().stackTrace)
        Log.w(className, createLog(message))
    }

    fun e(message: Any) {
        if (!isDebuggable) {
            return
        }

        getMethodNames(Throwable().stackTrace)
        Log.e(className, createLog(message))
    }

    fun wtf(message: String) {
        if (!isDebuggable) {
            return
        }

        getMethodNames(Throwable().stackTrace)
        Log.wtf(className, createLog(message))
    }
}  