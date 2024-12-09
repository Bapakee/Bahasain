package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.remote.api.ApiConfig
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.response.DataItemLearn
import kotlinx.coroutines.flow.firstOrNull

class ModuleRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {
    fun getModule(): LiveData<Result<List<DataItemLearn?>?>> = liveData {
        emit(Result.Loading)
        try {
            userPreferences.getSession().collect {
                val response = apiService.getModule()
                val module = response.data
                emit(Result.Success(module))
            }
        } catch (e: Exception) {
            if (e.message?.contains("401") == true) {
                val refreshToken = userPreferences.getSession().firstOrNull()?.refreshToken
                val newAccessToken = ApiConfig.refreshAccessToken(refreshToken, userPreferences)
                if (!newAccessToken.isNullOrEmpty()) {
                    emit(Result.Loading)
                    val retryResponse = apiService.getModule()
                    val module = retryResponse.data
                    emit(Result.Success(module))
                } else {
                    emit(Result.Error("Failed to refresh token: ${e.message}"))
                }
            } else {
                emit(Result.Error(e.message.toString()))
            }
        }
    }

    companion object {
        @Volatile
        private var instance: ModuleRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): ModuleRepository =
            instance ?: synchronized(this) {
                instance ?: ModuleRepository(apiService, userPreferences)
            }.also { instance = it }
    }
}