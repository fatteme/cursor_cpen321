package com.biteswipe.data.model

data class UserProfile(
    val id: String,
    val username: String,
    val email: String,
    val profileImageUrl: String? = null,
    val likesCount: Int = 0,
    val dislikesCount: Int = 0,
    val matchesCount: Int = 0
) 