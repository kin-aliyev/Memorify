package com.example.core_data.di

import com.example.core_data.repository.AuthRepositoryImpl
import com.example.core_data.repository.CollectionRepositoryImpl
import com.example.core_data.repository.WordRepositoryImpl
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.CollectionRepository
import com.example.core_domain.repository.WordRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindDeckRepository(
        impl: CollectionRepositoryImpl
    ): CollectionRepository

    @Binds
    @Singleton
    abstract fun bindWordRepository(
        impl: WordRepositoryImpl
    ): WordRepository
}