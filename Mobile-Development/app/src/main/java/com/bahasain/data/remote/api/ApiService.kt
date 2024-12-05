package com.bahasain.data.remote.api

import com.bahasain.data.remote.request.LevelRequest
import com.bahasain.data.remote.request.LoginRequest
import com.bahasain.data.remote.request.RefreshRequest
import com.bahasain.data.remote.request.RegisterRequest
import com.bahasain.data.remote.response.LevelResponse
import com.bahasain.data.remote.response.LoginResponse
import com.bahasain.data.remote.response.ModuleResponse
import com.bahasain.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body refreshToken: RefreshRequest
    ): LoginResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body refreshToken: RefreshRequest
    ): LoginResponse

    @POST("progress/level")
    suspend fun setLevel(
        @Body level : LevelRequest
    ): LevelResponse

    @GET("module")
    suspend fun getModule(): ModuleResponse
}