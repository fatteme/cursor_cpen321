package com.biteswipe.data.api

import com.biteswipe.data.model.UserProfile
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.IOException

interface UserApiService {
    @GET("users/profile")
    suspend fun getUserProfile(): UserProfile

    @POST("users/logout")
    suspend fun logout()

    @PUT("users/profile")
    suspend fun updateProfile(@Body profile: UserProfile): UserProfile

    @POST("users/preferences")
    suspend fun updatePreferences(@Body preferences: Map<String, Any>)

    @GET("users/preferences")
    suspend fun getPreferences(): Map<String, Any>
}

class UserApi(private val authToken: String? = null) {
    private val baseUrl = "http://localhost:3000/api/" // Update with your actual API URL

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val authInterceptor = { chain: okhttp3.Interceptor.Chain ->
        val request = chain.request().newBuilder().apply {
            authToken?.let { addHeader("Authorization", "Bearer $it") }
        }.build()
        chain.proceed(request)
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(UserApiService::class.java)

    suspend fun getUserProfile(): Result<UserProfile> = try {
        Result.success(apiService.getUserProfile())
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun logout(): Result<Unit> = try {
        apiService.logout()
        Result.success(Unit)
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun updateProfile(profile: UserProfile): Result<UserProfile> = try {
        Result.success(apiService.updateProfile(profile))
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun updatePreferences(preferences: Map<String, Any>): Result<Unit> = try {
        apiService.updatePreferences(preferences)
        Result.success(Unit)
    } catch (e: IOException) {
        Result.failure(e)
    }

    suspend fun getPreferences(): Result<Map<String, Any>> = try {
        Result.success(apiService.getPreferences())
    } catch (e: IOException) {
        Result.failure(e)
    }
} 