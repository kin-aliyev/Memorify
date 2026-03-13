package com.example.feature_home.domain.usecase

import com.example.core_domain.model.word.WordCard
import com.example.core_domain.repository.WordRepository
import com.example.core_domain.util.SrsCalculator
import javax.inject.Inject

class ReviewWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(word: WordCard, rating: Int): Result<Unit> {
        val newSrs = SrsCalculator.calculate(word.srs, rating)

        val updatedWord = word.copy(
            srs = newSrs,
            knowledgeLevel = newSrs.toKnowledgeLevel().name,
            reviewCount = word.reviewCount + 1,
            correctCount = if (rating > 0) word.correctCount + 1 else word.correctCount
        )
        return wordRepository.updateWord(updatedWord)
    }
}