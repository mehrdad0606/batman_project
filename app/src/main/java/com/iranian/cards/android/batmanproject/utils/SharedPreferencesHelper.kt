package com.iranian.cards.android.batmanproject.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.iranian.cards.android.batmanproject.App

class SharedPreferencesHelper {
    private var sharedPreferences: SharedPreferences

    constructor() {
        sharedPreferences =
            PreferenceManager.getDefaultSharedPreferences(App.instance?.applicationContext)
    }

    constructor(sharedPreferencesName: String?) {
        sharedPreferences = App.instance?.applicationContext
            ?.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE)!!
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun getLong(key: String?, defaultValue: Long?): Long {
        return sharedPreferences.getLong(key, defaultValue!!)
    }

    fun putInt(key: String?, value: Int): Boolean {
        return sharedPreferences.edit().putInt(key, value).commit()
    }

    fun putBoolean(key: String?, value: Boolean): Boolean {
        return sharedPreferences.edit().putBoolean(key, value).commit()
    }

    fun putString(key: String?, value: String?): Boolean {
        return sharedPreferences.edit().putString(key, value).commit()
    }

    fun putLong(key: String?, value: Long?): Boolean {
        return sharedPreferences.edit().putLong(key, value!!).commit()
    }

    fun clear(): Boolean {
        return sharedPreferences.edit().clear().commit()
    }
}