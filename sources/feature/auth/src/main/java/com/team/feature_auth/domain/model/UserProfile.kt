package com.team.feature_auth.domain.model

data class UserProfile(
    val id: String,
    val fullName: String,
    val avatarUrl: String?,
    val email: String,
)