package com.bahasain.di

import android.content.Context
import com.bahasain.data.UserRepository
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.pref.dataStore
import com.bahasain.data.remote.api.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService, pref)
    }
}