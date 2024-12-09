package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.response.DataItemFolklore
import com.bahasain.data.remote.response.DataItemHistorical
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