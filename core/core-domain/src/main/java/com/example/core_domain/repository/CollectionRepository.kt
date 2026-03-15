package com.example.core_domain.repository

import com.example.core_domain.model.collection.Collection
import kotlinx.coroutines.flow.Flow

interface CollectionRepository {
    fun getCollections(): Flow<List<Collection>>

    suspend fun createCollection(collection: Collection): Result<String>
    suspend fun updateCollection(collection: Collection): Result<Unit>
    suspend fun deleteCollection(collection: Collection): Result<Unit>
}