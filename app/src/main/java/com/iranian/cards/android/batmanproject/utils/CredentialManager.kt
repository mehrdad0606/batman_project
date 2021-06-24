package com.iranian.cards.android.batmanproject.utils

import android.util.Log

object CredentialManager {
    private const val PREFERENCES_FILE = "credential_data"


    val moviesAsString: String?
        get() =
            SharedPreferencesHelper(PREFERENCES_FILE).getString(Constants.MOVIES, "")

    fun saveMoviesAsString(moviesAsString: String?) {
        SharedPreferencesHelper(PREFERENCES_FILE).putString(Constants.MOVIES, moviesAsString)
    }

    @JvmStatic
    fun deleteCredential() {
        SharedPreferencesHelper(PREFERENCES_FILE).clear()
    }
}