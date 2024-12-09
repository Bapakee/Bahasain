package com.bahasain.di

import android.content.Context
import com.bahasain.data.repository.ModuleRepository
import com.bahasain.data.repository.UserRepository
import com.bahasain.data.repository.VocabRepository
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.pref.dataStore
import com.bahasain.data.remote.api.ApiConfig
import com.bahasain.data.repository.CultureRepository

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService(context)
        return UserRepository.getInstance(apiService, pref)
    }

    fun provideModuleRepository(context: Context): ModuleRepository {
        val pref = UserPreferences.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService(context)
        return ModuleRepository.getInstance(apiService, pref)
    }

    fun provideVocabRepository(context: Context): VocabRepository {
        val apiService = ApiConfig.getApiService(context)

        return VocabRepository.getInstance(apiService)
    }

    fun provideCulturalRepository(context: Context): CultureRepository {
        val apiService = ApiConfig.getApiService(context)

        return CultureRepository.getInstance(apiService)
    }
}