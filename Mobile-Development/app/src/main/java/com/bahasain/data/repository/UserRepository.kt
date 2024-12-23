package com.bahasain.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bahasain.data.Result
import com.bahasain.data.pref.UserModel
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.remote.api.ApiService
import com.bahasain.data.remote.request.LevelRequest
import com.bahasain.data.remote.request.LoginRequest
import com.bahasain.data.remote.request.RegisterRequest
import com.bahasain.data.remote.request.SettingRequest
import com.bahasain.data.remote.response.auth.LoginResponse
import com.bahasain.data.remote.response.auth.RegisterResponse
import com.bahasain.data.remote.response.learn.LevelResponse
import com.bahasain.data.remote.response.user.ProfileResponse
import com.bahasain.data.remote.response.user.SettingResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){
    fun register(registerRequest: RegisterRequest): LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.register(registerRequest)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, RegisterResponse::class.java)
            emit(Result.Error(errorBody.message.toString()))
        }
    }

    fun login(loginRequest: LoginRequest): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(loginRequest)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, LoginResponse::class.java)
            emit(Result.Error(errorBody.message.toString()))
        }
    }

    fun setLevel(levelRequest: LevelRequest): LiveData<Result<LevelResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.setLevel(levelRequest)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, LevelResponse::class.java)
            emit(Result.Error(errorBody.message.toString()))
        }
    }

    fun getProfile(): Flow<Result<ProfileResponse?>?> = flow {
        emit(Result.Loading)
        try {
            val response = apiService.getProfile()
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, LevelResponse::class.java)
            emit(Result.Error(errorBody.message.toString()))
        }
    }

    fun setting(settingRequest: SettingRequest): LiveData<Result<SettingResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.setting(settingRequest)
            emit(Result.Success(response))
        }catch (e: HttpException){
            val jsonString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonString, LevelResponse::class.java)
            emit(Result.Error(errorBody.message.toString()))
        }
    }
    suspend fun saveSession(userModel: UserModel) {
        userPreferences.saveSession(userModel)
    }

    fun getSession(): Flow<UserModel> {
        return userPreferences.getSession()
    }

    suspend fun logout() {
        userPreferences.logout()
    }

    companion object{
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreferences: UserPreferences
        ): UserRepository =
            instance ?: synchronized(this){
                instance ?: UserRepository(apiService, userPreferences)
            }.also { instance = it }
    }
}