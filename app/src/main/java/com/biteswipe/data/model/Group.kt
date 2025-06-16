package com.biteswipe.data.model

data class Group(
    val id: String,
    val name: String,
    val creatorId: String,
    val members: List<GroupMember>,
    val preferences: GroupPreferences,
    val status: GroupStatus = GroupStatus.ACTIVE,
    val createdAt: Long = System.currentTimeMillis()
)

data class GroupMember(
    val userId: String,
    val username: String,
    val profileImageUrl: String? = null,
    val role: GroupRole = GroupRole.MEMBER
)

data class GroupPreferences(
    val cuisineTypes: List<String> = emptyList(),
    val priceRange: PriceRange = PriceRange.MEDIUM,
    val maxDistance: Int = 5, // in kilometers
    val dietaryRestrictions: List<String> = emptyList()
)

enum class GroupStatus {
    ACTIVE,
    INACTIVE,
    DELETED
}

enum class GroupRole {
    CREATOR,
    ADMIN,
    MEMBER
}

enum class PriceRange {
    LOW,      // $
    MEDIUM,   // $$
    HIGH,     // $$$
    LUXURY    // $$$$
} 