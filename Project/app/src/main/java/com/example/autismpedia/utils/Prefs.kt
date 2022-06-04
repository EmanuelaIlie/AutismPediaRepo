package com.example.autismpedia.utils

import android.content.Context
import com.example.autismpedia.R

class Prefs(context: Context) {

    private val preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)

    var adminEnabled: Boolean
        get() = preferences.getBoolean(Constants.ADMIN_ENABLED, false)
        set(value) = preferences.edit().putBoolean(Constants.ADMIN_ENABLED, value).apply()
}