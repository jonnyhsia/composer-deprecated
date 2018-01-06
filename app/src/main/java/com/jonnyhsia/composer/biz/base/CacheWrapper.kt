package com.jonnyhsia.composer.biz.base

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

    /**
     * 缓存是否合法
     */
    fun isValid() = validRule(cache, cacheId)

    fun invalidate() {
        cache = null
        cacheId = null
    }
}