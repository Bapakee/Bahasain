package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.remote.api.ApiConfig
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.request.ProgressRequest
import com.bahasain.data.remote.response.learn.DataItemLearn
import com.bahasain.data.remote.response.learn.DataItemQuiz
import com.bahasain.data.remote.response.learn.DataItemScore
import com.bahasain.data.remote.response.learn.ProgressResponse
import com.bahasain.data.remote.response.learn.QuizResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

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
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getQuiz(moduleId: String, moduleLevel: String): LiveData<Result<List<DataItemQuiz>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getQuiz(moduleId, moduleLevel)
            val quiz = response.data
            emit(Result.Success(quiz))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    fun putProgress(progressRequest: ProgressRequest): LiveData<Result<ProgressResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.putProgress(progressRequest)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    fun getScore(): Flow<Result<DataItemScore?>?> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getScore()
            val score = response.data
            emit(Result.Success(score))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
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