package com.example.memorify.feature_auth.di

import  com.example.memorify.feature_auth.data.remote.provider.AndroidGoogleTokenProvider
import com.example.memorify.feature_auth.data.repository.AuthRepositoryImpl
import com.example.memorify.feature_auth.data.validation.EmailValidatorImpl
import com.example.memorify.feature_auth.data.validation.PasswordValidatorImpl
import com.example.memorify.feature_auth.domain.provider.AuthStringProvider
import com.example.memorify.feature_auth.domain.provider.GoogleTokenProvider
import com.example.memorify.feature_auth.domain.repository.AuthRepository
import com.example.memorify.feature_auth.domain.validation.EmailValidator
import com.example.memorify.feature_auth.domain.validation.PasswordValidator
import com.example.memorify.feature_auth.presentation.mapper.ResourceAuthStringProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Binds
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindEmailValidator(impl: EmailValidatorImpl): EmailValidator

    @Binds
    @Singleton
    abstract fun bindPasswordValidator(impl: PasswordValidatorImpl): PasswordValidator

    @Binds
    @Singleton
    abstract fun bindGoogleTokenProvider(impl: AndroidGoogleTokenProvider): GoogleTokenProvider

    @Binds
    @Singleton
    abstract fun bindAuthStringProvider(impl: ResourceAuthStringProvider): AuthStringProvider
}