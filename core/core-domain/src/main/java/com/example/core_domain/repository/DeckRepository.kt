package com.example.core_domain.repository

import com.example.core_domain.model.deck.Deck
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
    fun getDecks(): Flow<List<Deck>>

    suspend fun createDeck(deck: Deck): Result<String>
    suspend fun updateDeck(deck: Deck): Result<Unit>
    suspend fun deleteDeck(deck: Deck): Result<Unit>
}