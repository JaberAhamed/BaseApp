package com.sj.baseapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.sj.baseapp.models.User
import com.squareup.moshi.Moshi
import javax.inject.Inject

class UserSharedPreference @Inject constructor(
    context: Context
) {
    companion object {
        private const val PREF_TOKEN = "token"
        private const val PREF_USER = "user"
        private const val PERMISSION_URL = "Url"
    }

    private val context = context.applicationContext

    @Volatile
    private var userSharePreference: SharedPreferences? = null

    @Volatile
    private var user: User? = null

    private fun getUserSharePreference(): SharedPreferences = userSharePreference ?: synchronized(this) {
        context.getSharedPreferences(
            //  "${BuildConfig.APPLICATION_ID}.main",
            "",
            Context.MODE_PRIVATE
        )
    }

    fun reset() {
        getUserSharePreference().edit().clear().apply()
        user = null
    }

    fun setToken(token: String) {
        Log.d("Log404", "setToken: set token ")
        getUserSharePreference().edit().apply {
            putString(PREF_TOKEN, token)
            apply()
        }
    }

    fun getToken(): String? {
        Log.d("Log404", "setToken: get token-> ${getUserSharePreference().getString(PREF_TOKEN, null)}")

        return getUserSharePreference().getString(PREF_TOKEN, null)
    }

    fun setUser(user: User?) {
        getUserSharePreference().edit().apply {
            val moshi = Moshi.Builder()
                .build()
            val jsonAdapter = moshi.adapter(User::class.java)
            putString(PREF_USER, jsonAdapter.toJson(user))
            apply()
        }
    }

    fun getUser(): User? = user ?: synchronized(this) {
        getUserSharePreference().let {
            val moshi = Moshi.Builder()
                .build()
            val jsonAdapter = moshi.adapter(User::class.java)
            val userJson = it.getString(PREF_USER, null)
            user = if (userJson == null) {
                null
            } else {
                jsonAdapter.fromJson(userJson)
            }

            user
        }
    }

    fun setUrl(token: String) {
        Log.d("Log404", "saveUrl: $token")
        getUserSharePreference().edit().apply {
            putString(PERMISSION_URL, token)
            apply()
        }
    }

    fun getUrl(): String? {
        if (getUserSharePreference().getString(PERMISSION_URL, null) == null) {
            return ""
        } else {
            return getUserSharePreference().getString(PERMISSION_URL, null)
        }
    }
}
