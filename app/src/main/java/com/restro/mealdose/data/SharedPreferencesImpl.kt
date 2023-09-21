package com.app.compose_structure.data.local

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : ISharedPreferences {

    companion object {
        const val PREFERENCE_NAME = "user_preference"
    }

    private val sharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    private var editor = sharedPreferences.edit()

    override suspend fun storeValue(key: String, value: Any) {
        if (value is String) {
            editor.putString(key, value).apply()
        } else if (value is Boolean) {
            editor.putBoolean(key, value).apply()
        }
    }

    override suspend fun <T> getValue(key: String, default: T): T {
        return when (default) {
            is String -> sharedPreferences.getString(key, default) as T
            is Boolean -> sharedPreferences.getBoolean(key, default) as T
            else -> sharedPreferences.getString(key, default as String) as T
        }
    }

    override suspend fun clear() {
        editor.clear()
    }


}