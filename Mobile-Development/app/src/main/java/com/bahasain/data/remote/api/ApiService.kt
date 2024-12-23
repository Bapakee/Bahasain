package com.bahasain.data.remote.api

import com.bahasain.data.remote.request.LevelRequest
import com.bahasain.data.remote.request.LoginRequest
import com.bahasain.data.remote.request.ProgressRequest
import com.bahasain.data.remote.request.RefreshRequest
import com.bahasain.data.remote.request.RegisterRequest
import com.bahasain.data.remote.request.SettingRequest
import com.bahasain.data.remote.request.TranslateRequest
import com.bahasain.data.remote.response.cultural.FolkloreResponse
import com.bahasain.data.remote.response.cultural.HistoricalResponse
import com.bahasain.data.remote.response.learn.LevelResponse
import com.bahasain.data.remote.response.auth.LoginResponse
import com.bahasain.data.remote.response.auth.LogoutResponse
import com.bahasain.data.remote.response.auth.RefreshResponse
import com.bahasain.data.remote.response.learn.ModuleResponse
import com.bahasain.data.remote.response.auth.RegisterResponse
import com.bahasain.data.remote.response.cultural.DetailFolkloreResponse
import com.bahasain.data.remote.response.cultural.DetailHistoricalResponse
import com.bahasain.data.remote.response.cultural.DetailRecipeResponse
import com.bahasain.data.remote.response.cultural.RecipeResponse
import com.bahasain.data.remote.response.learn.ProgressResponse
import com.bahasain.data.remote.response.learn.QuizResponse
import com.bahasain.data.remote.response.learn.ScoreResponse
import com.bahasain.data.remote.response.user.ProfileResponse
import com.bahasain.data.remote.response.user.SettingResponse
import com.bahasain.data.remote.response.vocab.TranslateResponse
import com.bahasain.data.remote.response.vocab.TriviaResponse
import com.bahasain.data.remote.response.vocab.WordCategoriesResponse
import com.bahasain.data.remote.response.vocab.WordDetailResponse
import com.bahasain.data.remote.response.vocab.WotdResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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
    ): RefreshResponse

    @GET("profile")
    suspend fun getProfile(): ProfileResponse

    @POST("auth/logout")
    suspend fun logout(
        @Body refreshToken: RefreshRequest
    ): LogoutResponse

    @POST("progress/level")
    suspend fun setLevel(
        @Body level: LevelRequest
    ): LevelResponse

    @PUT("progress")
    suspend fun putProgress(
        @Body progress: ProgressRequest
    ): ProgressResponse

    @GET("profile/head")
    suspend fun getScore():ScoreResponse

    @GET("module")
    suspend fun getModule(): ModuleResponse

    @GET("module/{id}/level/{level}")
    suspend fun getQuiz(
        @Path("id") moduleId : String,
        @Path("level") moduleLevel : String
    ): QuizResponse

    @GET("word?limit=100")
    suspend fun getWordCategories(
        @Query("categories") categories: String
    ): WordCategoriesResponse

    @POST("translate")
    suspend fun translate(
        @Body translateRequest: TranslateRequest
    ): TranslateResponse

    @GET("word/0")
    suspend fun getWotd(): WotdResponse

    @GET("word/{id}")
    suspend fun getDetailWord(
        @Path("id") wordId: String
    ):WordDetailResponse

    @GET("trivia")
    suspend fun getTrivia(): TriviaResponse

    @GET("cultural/historical")
    suspend fun getHistorical(): HistoricalResponse

    @GET("cultural/historical/{id}")
    suspend fun getDetailHistorical(
        @Path("id") historicalId : String
    ): DetailHistoricalResponse

    @GET("cultural/folklore")
    suspend fun getFolklore(): FolkloreResponse

    @GET("cultural/folklore/{id}")
    suspend fun getDetailFolklore(
        @Path("id") folkloreId: String
    ): DetailFolkloreResponse

    @GET("cultural/recipe")
    suspend fun getRecipe(): RecipeResponse

    @GET("cultural/recipe/{id}")
    suspend fun getDetailRecipe(
        @Path("id") recipeId: String
    ): DetailRecipeResponse

    @POST("setting")
    suspend fun setting(
        @Body settingRequest: SettingRequest
    ): SettingResponse
}