package com.example.core_domain.usecase.home

import com.example.core_domain.model.collection.CollectionDetail
import com.example.core_domain.repository.CollectionRepository
import com.example.core_domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetCollectionDetailUseCase @Inject constructor(
    private val collectionRepository: CollectionRepository,
    private val wordRepository: WordRepository,
) {
    operator fun invoke(collectionId: String): Flow<CollectionDetail> =
        combine(
            collectionRepository.getCollections(),
            wordRepository.getWords(collectionId)
        ) { collections, words ->
            val collection = collections.find { it.id == collectionId }
                ?: return@combine null
            CollectionDetail(collection, words)
        }.filterNotNull()
}