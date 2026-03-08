package com.example.feature_home.domain.usecase

import com.example.core_domain.model.deck.Deck
import com.example.core_domain.repository.DeckRepository
import javax.inject.Inject

class CreateDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck): Result<String> =
        deckRepository.createDeck(deck)
}