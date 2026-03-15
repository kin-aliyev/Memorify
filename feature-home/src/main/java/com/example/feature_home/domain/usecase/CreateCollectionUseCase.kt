package com.example.feature_home.domain.usecase

import com.example.core_domain.model.collection.Collection
import com.example.core_domain.repository.CollectionRepository
import javax.inject.Inject

class CreateCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(collection: Collection): Result<String> =
        collectionRepository.createCollection(collection)
}