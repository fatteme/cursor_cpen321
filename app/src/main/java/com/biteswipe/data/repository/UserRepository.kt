package com.biteswipe.data.repository

import com.biteswipe.data.api.UserApi
import com.biteswipe.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository {
    private val api = UserApi()

    suspend fun getUserProfile(): UserProfile = withContext(Dispatchers.IO) {
        // TODO: Implement actual API call
        // For now, return mock data
        UserProfile(
            id = "1",
            username = "John Doe",
            email = "john.doe@example.com",
            profileImageUrl = "https://example.com/profile.jpg",
            likesCount = 42,
            dislikesCount = 15,
            matchesCount = 8
        )
    }

    suspend fun logout() = withContext(Dispatchers.IO) {
        // TODO: Implement actual API call
        api.logout()
    }
}