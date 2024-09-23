package ru.chantreck.brics2024

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesProvider {
    private const val PREFERENCES = "PREFERENCES"
    private const val KEY_USER_ID = "USER_ID"

    private var preferences: SharedPreferences? = null

    fun setup(context: Context) {
        preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun getUserId(): String? {
        val instance = preferences ?: return null
        return instance.getString(KEY_USER_ID, null)
    }

    fun saveUserId(userId: String) {
        val instance = preferences ?: return
        with(instance.edit()) {
            putString(KEY_USER_ID, userId)
            apply()
        }
    }
}
