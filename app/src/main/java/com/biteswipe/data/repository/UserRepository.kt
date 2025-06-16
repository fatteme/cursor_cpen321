package com.biteswipe.data.repository

import com.biteswipe.data.api.UserApi
import com.biteswipe.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val authToken: String? = null) {
    private val api = UserApi(authToken)

    suspend fun getUserProfile(): Result<UserProfile> = withContext(Dispatchers.IO) {
        try {
            api.getUserProfile()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun logout(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            api.logout()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateProfile(profile: UserProfile): Result<UserProfile> = withContext(Dispatchers.IO) {
        try {
            api.updateProfile(profile)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePreferences(preferences: Map<String, Any>): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            api.updatePreferences(preferences)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getPreferences(): Result<Map<String, Any>> = withContext(Dispatchers.IO) {
        try {
            api.getPreferences()
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}