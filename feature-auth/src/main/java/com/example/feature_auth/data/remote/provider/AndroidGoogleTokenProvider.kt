package com.example.feature_auth.data.remote.provider

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import androidx.credentials.exceptions.NoCredentialException
import com.example.feature_auth.domain.exception.AuthException
import com.example.feature_auth.domain.provider.GoogleTokenProvider
import com.example.memorify.feature_auth.BuildConfig
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import javax.inject.Inject

class AndroidGoogleTokenProvider @Inject constructor() : GoogleTokenProvider {

    override suspend fun getIdToken(context: Context): Result<String> {
        return try {
            val serverClientId = BuildConfig.WEB_CLIENT_ID

            val signInWithGoogleOption = GetSignInWithGoogleOption
                .Builder(serverClientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(signInWithGoogleOption)
                .build()

            val credentialResponse = CredentialManager.create(context)
                .getCredential(
                    request = request,
                    context = context
                )

            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(
                credentialResponse.credential.data
            )

            Result.success(googleIdTokenCredential.idToken)

        } catch (e: NoCredentialException) {
            Log.d(TAG, "No Google credentials available", e)
            Result.failure(AuthException.GoogleAccountNotFound)
        } catch (e: GetCredentialCancellationException) {
            Log.d(TAG, "User cancelled Google sign-in", e)
            Result.failure(AuthException.GoogleAuthCancelled)
        } catch (e: GetCredentialException) {
            Log.d(TAG, "Failed to get Google credentials", e)
            Result.failure(AuthException.GoogleAuthFailed)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during Google sign-in", e)
            Result.failure(AuthException.UnknownError(e, "Google sign-in failed"))
        }
    }

    companion object {
        private const val TAG = "GoogleTokenProvider"
    }
}