package com.example.feature_home.domain.usecase

import com.example.core_domain.model.deck.Deck
import com.example.core_domain.repository.DeckRepository
import javax.inject.Inject

class UpdateDeckUseCase @Inject constructor(
    private val deckRepository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck): Result<Unit> =
        deckRepository.updateDeck(deck)
}