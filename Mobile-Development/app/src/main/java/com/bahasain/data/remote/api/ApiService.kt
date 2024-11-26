package com.bahasain.data.remote.api

import com.bahasain.data.remote.request.LoginRequest
import com.bahasain.data.remote.request.RegisterRequest
import com.bahasain.data.remote.response.LoginResponse
import com.bahasain.data.remote.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
}