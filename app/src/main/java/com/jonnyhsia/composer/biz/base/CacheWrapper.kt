package com.jonnyhsia.composer.biz.base

import com.jonnyhsia.composer.kit.Debug

/**
 * @author JonnyHsia on 18/1/1.
 */
class CacheWrapper<T>(
        private val validRule: (cache: T?, id: String?) -> Boolean = { cache, id ->
            // 缓存与 ID 非空且缓存 ID 对应用户 ID
            cache != null && id != null && id == BaseLogic.getUsername()
        }
) {

    var cache: T? = null
        private set

    var cacheId: String? = null
        private set

    fun update(newData: T, newCacheId: String) {
        cache = newData
        cacheId = newCacheId
    }

    fun take(): T? {
        return if (isValid()) {
            cache
        } else {
            Debug.e("缓存已经失效")
            null
        }
    }

    /**
     * 缓存是否合法
     */
    fun isValid() = validRule(cache, cacheId)

    fun invalidate() {
        cache = null
        cacheId = null
    }
}