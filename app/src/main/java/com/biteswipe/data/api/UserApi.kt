package com.biteswipe.data.api

import com.biteswipe.data.model.UserProfile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @GET("users/profile")
    suspend fun getUserProfile(): UserProfile

    @POST("users/logout")
    suspend fun logout()
}

class UserApi {
    private val baseUrl = "http://localhost:3000/api/" // TODO: Replace with actual API URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(UserApiService::class.java)

    suspend fun getUserProfile(): UserProfile {
        return apiService.getUserProfile()
    }

    suspend fun logout() {
        apiService.logout()
    }
} 