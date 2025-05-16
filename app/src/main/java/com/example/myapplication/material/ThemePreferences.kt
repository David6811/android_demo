package com.example.myapplication.material

import android.content.Context

object ThemePreferences {
    private const val PREF_NAME = "theme_prefs"
    private const val KEY_PRIMARY_COLOR = "primary_color"

    fun savePrimaryColor(context: Context, color: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_PRIMARY_COLOR, color).apply()
    }

    fun getPrimaryColor(context: Context, defaultColor: Int): Int {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getInt(KEY_PRIMARY_COLOR, defaultColor)
    }
}