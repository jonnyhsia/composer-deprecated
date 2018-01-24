package com.jonnyhsia.composer.kit

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class Disposable<in R, T>(private var value: T? = null) : ReadWriteProperty<R?, T?> {

    override fun getValue(thisRef: R?, property: KProperty<*>): T? {
        // 取出值后将值置空
        return value.also { value = null }
    }

    override fun setValue(thisRef: R?, property: KProperty<*>, value: T?) {
        this.value = value
    }
}