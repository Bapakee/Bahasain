package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.request.TranslateRequest
import com.bahasain.data.remote.response.learn.LevelResponse
import com.bahasain.data.remote.response.vocab.DataItemDetailWord
import com.bahasain.data.remote.response.vocab.DataItemWord
import com.bahasain.data.remote.response.vocab.DataTranslate
import com.bahasain.data.remote.response.vocab.TriviaResponse
import com.bahasain.data.remote.response.vocab.WotdResponse
import com.google.gson.Gson
import retrofit2.HttpException

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
            } catch (e: HttpException){
                val jsonString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonString, LevelResponse::class.java)
                emit(Result.Error(errorBody.message.toString()))
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

    fun getDetailWord(wordId: String): LiveData<Result<DataItemDetailWord?>?> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailWord(wordId)
            val word = response.data
            emit(Result.Success(word))
        }catch (e: Exception) {
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