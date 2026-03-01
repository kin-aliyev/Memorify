package com.example.memorify.feature_auth.domain.provider

import android.content.Context

interface GoogleTokenProvider {
    suspend fun getIdToken(context: Context): Result<String>
}