package com.bahasain.data.remote.api

import android.content.Context
import android.content.Intent
import android.util.Log
import com.bahasain.data.pref.UserPreferences
import com.bahasain.data.pref.dataStore
import com.bahasain.data.remote.request.RefreshRequest
import com.bahasain.ui.auth.login.LoginActivity
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(context: Context): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val authInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()

            val userPreferences = UserPreferences.getInstance(context.dataStore)
            val accessToken = runBlocking { userPreferences.getSession().firstOrNull()?.accessToken }

            val requestWithToken = originalRequest.newBuilder()
                .header("Authorization", "Bearer $accessToken")
                .build()

            var response = chain.proceed(requestWithToken)

            if (response.code == 401) {
                val refreshToken = runBlocking { userPreferences.getSession().firstOrNull()?.refreshToken }

                synchronized(this) {
                    val newAccessToken = runBlocking {
                        refreshAccessToken(refreshToken, userPreferences)
                    }

                    if (newAccessToken != null) {
                        val newRequest = originalRequest.newBuilder()
                            .header("Authorization", "Bearer $newAccessToken")
                            .build()

                        response.close()
                        response = chain.proceed(newRequest)
                    } else {
                        runBlocking { logoutUser(userPreferences, context) }
                    }
                }
            }
            response
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://backend-app-238480322773.asia-southeast2.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private fun refreshAccessToken(refreshToken: String?, userPreferences: UserPreferences): String? {
        if (refreshToken.isNullOrEmpty()) return null

        return try {
            val apiService = getRefreshTokenService()
            val response = runBlocking {
                apiService.refreshToken(RefreshRequest(refreshToken))
            }

            if (response.data?.accessToken != null) {
                val newAccessToken = response.data.accessToken
                runBlocking {
                    val currentSession = userPreferences.getSession().firstOrNull()
                    if (currentSession != null) {
                        userPreferences.saveSession(
                            currentSession.copy(accessToken = newAccessToken)
                        )
                    }
                }
                newAccessToken
            } else {
                Log.e("RefreshToken", "Refresh token failed: ${response.message}")
                null
            }
        } catch (e: Exception) {
            Log.e("RefreshToken", "Exception occurred: ${e.message}")
            null
        }
    }

    private fun getRefreshTokenService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://backend-app-238480322773.asia-southeast2.run.app/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    private suspend fun logoutUser(userPreferences: UserPreferences, context: Context) {
        userPreferences.logout()

        val intent = Intent(context, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        context.startActivity(intent)
    }

}
