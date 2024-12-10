package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.response.cultural.DataItemFolklore
import com.bahasain.data.remote.response.cultural.DataItemHistorical
import com.bahasain.data.remote.response.cultural.DataItemRecipe
import com.bahasain.data.remote.response.cultural.DetailFolkloreResponse
import com.bahasain.data.remote.response.cultural.DetailHistoricalResponse
import com.bahasain.data.remote.response.cultural.DetailRecipeResponse
import retrofit2.HttpException

class CultureRepository(
    private val apiService: ApiService
) {

    fun getHistorical(): LiveData<Result<List<DataItemHistorical?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getHistorical()
            val historical = response.data
            emit(Result.Success(historical))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    fun getDetailHistorical(historicalId: String): LiveData<Result<DetailHistoricalResponse?>?> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailHistorical(historicalId)
            emit(Result.Success(response))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFolklore(): LiveData<Result<List<DataItemFolklore?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFolklore()
            val folklore = response.data
            emit(Result.Success(folklore))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    fun getDetailFolklore(folkloreId: String): LiveData<Result<DetailFolkloreResponse?>?> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailFolklore(folkloreId)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    fun getRecipe(): LiveData<Result<List<DataItemRecipe?>?>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRecipe()
            val recipe = response.data
            emit(Result.Success(recipe))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    fun getDetailRecipe(recipeId: String): LiveData<Result<DetailRecipeResponse?>?> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailRecipe(recipeId)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(e.message().toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: CultureRepository? = null
        fun getInstance(
            apiService: ApiService
        ): CultureRepository =
            instance ?: synchronized(this) {
                instance ?: CultureRepository(apiService)
            }.also { instance = it }
    }
}