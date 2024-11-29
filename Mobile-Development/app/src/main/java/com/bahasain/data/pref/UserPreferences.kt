package com.bahasain.data.pref

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveSession(user: UserModel) {
        Log.d("UserPreferences", "Saving session: $user")
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = user.accessToken
            preferences[REFRESH_TOKEN_KEY] = user.refreshToken

//            preferences[ID_KEY] = user.id
//            preferences[NAME_KEY] = user.userName
//            preferences[ISNEW_KEY] = user.isNew
//            preferences[LEVEL_KEY] = user.userLevel
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[ACCESS_TOKEN_KEY] ?: "",
                preferences[REFRESH_TOKEN_KEY] ?: "",
//
//                preferences[ID_KEY] ?: "",
//                preferences[NAME_KEY] ?: "",
//                preferences[LEVEL_KEY] ?: "",
//                preferences[ISNEW_KEY] ?: false,
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
        Log.d("UserPreferences", "Session cleared successfully")
        val currentSession = dataStore.data.first()
        Log.d("Logout", "Session after clear: $currentSession")
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null


        private val ACCESS_TOKEN_KEY = stringPreferencesKey("token")
        private val REFRESH_TOKEN_KEY = stringPreferencesKey("token")
//
//        private val ID_KEY = stringPreferencesKey("id")
//        private val NAME_KEY = stringPreferencesKey("name")
//        private val ISNEW_KEY = booleanPreferencesKey("isNew")
//        private val LEVEL_KEY = stringPreferencesKey("level")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}