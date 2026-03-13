package com.example.core_data.repository

import com.example.core_data.mapper.toDeck
import com.example.core_data.mapper.toMap
import com.example.core_domain.exception.AppException
import com.example.core_domain.model.deck.Deck
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.DeckRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeckRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
):  DeckRepository {

    private suspend fun uid(): String = authRepository.currentUser.first()?.id
        ?: throw AppException.UserNotFound

    private fun decksCollection(uid: String) = firestore
        .collection("users")
        .document(uid)
        .collection("decks")

    override fun getDecks(): Flow<List<Deck>> = callbackFlow{
        val uid = try { uid() } catch (e: Exception) { close(e); return@callbackFlow }

        val listener = decksCollection(uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) { close(exception); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toDeck() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override suspend fun createDeck(deck: Deck): Result<String> = runCatching {
        val col = decksCollection(uid())
        val doc = col.document()
        doc.set(deck.copy(id = doc.id).toMap()).await()
        doc.id
    }

    override suspend fun updateDeck(deck: Deck): Result<Unit> = runCatching {
        decksCollection(uid()).document(deck.id).set(deck.toMap()).await()
    }

    override suspend fun deleteDeck(deck: Deck): Result<Unit> = runCatching {
        decksCollection(uid()).document(deck.id).delete().await()
    }

}