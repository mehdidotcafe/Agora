package io.agora.agora.entities

import android.content.Context
import android.content.Context.MODE_PRIVATE

object Storage {

    private val PREFS_NAME = "io.agora.agora.PREFS"

    fun get(ctx: Context, key: String): String? {
        val prefs = ctx.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        return prefs.getString(key, null)
    }

    fun put(ctx: Context, key: String, token: String): String {
        val editor = ctx.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
        editor.putString(key, token)
        editor.apply()

        return token
    }
}
