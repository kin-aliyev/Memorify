package com.example.feature_home.domain.usecase

import com.example.core_domain.model.deck.Collection
import com.example.core_domain.repository.CollectionRepository
import javax.inject.Inject

class UpdateCollectionUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    suspend operator fun invoke(deck: Collection): Result<Unit> =
        collectionRepository.updateCollection(deck)
}