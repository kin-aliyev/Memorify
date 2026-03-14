package com.example.core_domain.repository

import com.example.core_domain.model.deck.Collection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getCollections(): Flow<List<Collection>>

    suspend fun createCollection(deck: Collection): Result<String>
    suspend fun updateCollection(deck: Collection): Result<Unit>
    suspend fun deleteCollection(deck: Collection): Result<Unit>
}