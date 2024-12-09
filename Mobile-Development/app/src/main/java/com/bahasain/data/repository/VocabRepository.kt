package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.request.TranslateRequest
import com.bahasain.data.remote.response.DataItemWord
import com.bahasain.data.remote.response.DataTranslate
import com.bahasain.data.remote.response.TriviaResponse
import com.bahasain.data.remote.response.WotdResponse

class VocabRepository(
    private val apiService: ApiService
) {
    fun getWordCategories(wordCategories: String): LiveData<Result<List<DataItemWord?>?>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.getWordCategories(wordCategories)
                val word = response.data
                emit(Result.Success(word))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun translate(translateRequest: TranslateRequest): LiveData<Result<DataTranslate?>?> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.translate(translateRequest)
                val wordResult = response.data
                emit(Result.Success(wordResult))
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun getWotd(): LiveData<Result<WotdResponse?>?> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getWotd()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getTrivia(): LiveData<Result<TriviaResponse?>?> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getTrivia()
            emit(Result.Success(response))
        }catch(e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: VocabRepository? = null
        fun getInstance(
            apiService: ApiService
        ): VocabRepository =
            instance ?: synchronized(this) {
                instance ?: VocabRepository(apiService)
            }.also { instance = it }
    }
}