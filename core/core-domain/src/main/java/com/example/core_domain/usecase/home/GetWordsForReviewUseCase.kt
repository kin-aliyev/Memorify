package com.example.core_domain.usecase.home

import com.example.core_domain.model.word.WordCard
import com.example.core_domain.repository.WordRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWordsForReviewUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    operator fun invoke(): Flow<List<WordCard>> =
        wordRepository.getWordsForReview()

    operator fun invoke(deckId: String) : Flow<List<WordCard>> =
        wordRepository.getWordsForReview(deckId)
}