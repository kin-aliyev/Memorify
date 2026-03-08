package com.example.core_domain.repository

import com.example.core_domain.model.word.WordCard
import kotlinx.coroutines.flow.Flow

interface WordRepository {
    fun getWords(deckId: String): Flow<List<WordCard>>

    fun getWordsForReview(): Flow<List<WordCard>>
    fun getWordsForReview(deckId: String): Flow<List<WordCard>>

    suspend fun addWord(word: WordCard): Result<String>
    suspend fun updateWord(word: WordCard): Result<Unit>
    suspend fun deleteWord(word: WordCard): Result<Unit>

}