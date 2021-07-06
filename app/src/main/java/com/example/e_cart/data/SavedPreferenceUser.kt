package com.example.e_cart.data

import android.content.SharedPreferences
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

object SavedPreferenceUser {
    private lateinit var user: GoogleSignInAccount

    fun setUser(preferences: SharedPreferences?, userGoogle: GoogleSignInAccount) {
        user = userGoogle
        val edit = preferences?.edit()
        user.email?.let { Log.d("SharedTAG", it) }

        edit!!.putString("EMAIL", user.email)?.apply()
        edit.putString("USERNAME", user.displayName)?.apply()
        edit.putString("ID", user.id)?.apply()

    }
}