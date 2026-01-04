package com.team.preferences.data.delegates.api

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** Root interface for all preferences delegates */
abstract class TypedPreference<T>(
    val preferences: SharedPreferences
) : ReadWriteProperty<Any?, T> {

    abstract fun getPreference(): T
    abstract fun setPreference(value: T)

    override operator fun getValue(thisRef: Any?, property: KProperty<*>): T = getPreference()

    override operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        preferences.edit { setPreference(value) }
    }
}