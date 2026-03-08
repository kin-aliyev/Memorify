package com.example.core_domain.usecase.home

import com.example.core_domain.model.deck.Deck
import com.example.core_domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDecksUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    operator fun invoke(): Flow<List<Deck>> =
        deckRepository.getDecks()
}