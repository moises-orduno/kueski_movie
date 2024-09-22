package com.example.mazeapi.sharedpreferences.repository

import android.content.SharedPreferences
import com.example.mazeapi.sharedpreferences.model.UserPreferences
import com.google.gson.Gson
import javax.inject.Inject

class SharedPreferencesRepository @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    /**
     * Saves the user preferences as a JSON string in SharedPreferences.
     *
     * @param userPreferences The user preferences object to be saved.
     *                       This object will be converted to a JSON string.
     */
    fun saveUserPreferences(userPreferences: UserPreferences) {
        val gson = Gson()
        val jsonString = gson.toJson(userPreferences)
        sharedPreferences.edit().putString(USER_SHARED_PREFERENCES, jsonString).apply()
    }

    /**
     * Retrieves the user preferences from SharedPreferences.
     *
     * @return The user preferences object retrieved from SharedPreferences,
     *         or null if no preferences are found. The JSON string stored
     *         in SharedPreferences will be converted back to a UserPreferences object.
     */
    fun getUserPreferences(): UserPreferences? {
        val gson = Gson()
        // Default value
        val userPreferencesDefaultValue = UserPreferences()
        val defaultJsonString = gson.toJson(userPreferencesDefaultValue)

        val jsonString = sharedPreferences.getString(USER_SHARED_PREFERENCES, defaultJsonString)
        return jsonString?.let {
            gson.fromJson(it, UserPreferences::class.java)
        }
    }

    companion object {
        const val USER_SHARED_PREFERENCES = "USER_SHARED_PREFERENCES"
    }
}