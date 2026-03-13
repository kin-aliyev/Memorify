package com.example.core_data.mapper

import com.example.core_domain.model.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomain(): User = User(
    id = uid,
    email = email ?: throw IllegalArgumentException("User email is required"),
    displayName = displayName,
)