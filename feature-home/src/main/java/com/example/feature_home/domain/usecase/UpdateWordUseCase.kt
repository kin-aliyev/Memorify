package com.example.feature_home.domain.usecase

import com.example.core_domain.model.word.WordCard
import com.example.core_domain.repository.WordRepository
import javax.inject.Inject

class UpdateWordUseCase @Inject constructor(
    private val wordRepository: WordRepository
) {
    suspend operator fun invoke(word: WordCard): Result<Unit> =
        wordRepository.updateWord(word)
}