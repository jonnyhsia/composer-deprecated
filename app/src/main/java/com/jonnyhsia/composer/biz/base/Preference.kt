package com.jonnyhsia.composer.biz.base

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by JonnyHsia on 17/8/7.
 * Preference delegate
 */
class Preference<T>(val context: Context,
                    private val key: String,
                    private val default: T,
                    private val name: String? = null,
                    private val onChange: ((newValue: T) -> Unit)? = null)
    : ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        if (name == null) {
            PreferenceManager.getDefaultSharedPreferences(context)
        } else {
            context.getSharedPreferences(name, Context.MODE_PRIVATE)
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPref(key, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        setPref(key, value)
        onChange?.invoke(value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun <U> setPref(name: String, value: U) = with(prefs.edit()) {
        when (value) {
            is Int -> putInt(name, value)
            is String -> putString(name, value)
            is Float -> putFloat(name, value)
            is Boolean -> putBoolean(name, value)
            is Long -> putLong(name, value)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences.")
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> findPref(name: String, default: T) = with(prefs) {
        val res: Any = when (default) {
            is Int -> getInt(name, default)
            is String -> getString(name, default)
            is Float -> getFloat(name, default)
            is Boolean -> getBoolean(name, default)
            is Long -> getLong(name, default)
            else -> throw IllegalArgumentException("This type cannot be saved into Preferences.")
        }
        res as T
    }

    companion object {
        val NAME_PASSPORT = "passport"
        val NAME_CONFIG = "config"
    }
}