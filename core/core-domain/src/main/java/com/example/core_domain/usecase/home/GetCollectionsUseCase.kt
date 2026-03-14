package com.example.core_domain.usecase.home

import com.example.core_domain.model.deck.Collection
import com.example.core_domain.repository.CollectionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCollectionsUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository
) {
    operator fun invoke(): Flow<List<Collection>> = collectionRepository.getCollections()
}