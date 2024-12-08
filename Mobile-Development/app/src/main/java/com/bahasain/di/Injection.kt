package com.bahasain.di

import android.content.Context
import com.bahasain.data.ModuleRepository
import com.bahasain.data.UserRepository
import com.bahasain.data.VocabRepository
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.pref.dataStore
import com.bahasain.data.remote.api.ApiConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreferences.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService(context)
        return UserRepository.getInstance(apiService, pref)
    }

    fun ProvideModuleRepository(context: Context): ModuleRepository {
        val pref = UserPreferences.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService(context)
        return ModuleRepository.getInstance(apiService, pref)
    }

    fun ProvideVocabRepository(context: Context): VocabRepository{
        val pref = UserPreferences.getInstance(context.dataStore)

        val apiService = ApiConfig.getApiService(context)
        return VocabRepository.getInstance(apiService, pref)
    }
}