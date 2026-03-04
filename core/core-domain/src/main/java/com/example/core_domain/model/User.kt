package com.example.core_domain.model

data class User(
    val id: String,
    val email: String,
    val displayName: String? = null,
) {
    init {
        require(id.isNotBlank()) { "User ID cannot be blank" }
        require(email.isNotBlank()) { "Email cannot be blank" }
    }
}
