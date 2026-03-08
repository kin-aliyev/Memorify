package com.example.core_data.repository

import com.example.core_data.mapper.toMap
import com.example.core_data.mapper.toWordCard
import com.example.core_domain.exception.AppException
import com.example.core_domain.model.word.WordCard
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.WordRepository
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
class WordRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
): WordRepository {
    private suspend fun uid(): String = authRepository.currentUser.first()?.id
        ?: throw AppException.UserNotFound

    private fun wordsCollection(uid: String) = firestore
        .collection("users")
        .document(uid)
        .collection("words")

    override fun getWords(deckId: String): Flow<List<WordCard>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e); return@callbackFlow}

        val listener = wordsCollection(uid)
            .whereEqualTo("deckId", deckId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toWordCard() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override fun getWordsForReview(): Flow<List<WordCard>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e); return@callbackFlow }
        val now = System.currentTimeMillis()

        val listener = wordsCollection(uid)
            .whereLessThanOrEqualTo("srs.nextReviewAt", now)
            .orderBy("srs.nextReviewAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toWordCard() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override fun getWordsForReview(deckId: String): Flow<List<WordCard>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e); return@callbackFlow }
        val now = System.currentTimeMillis()

        val listener = wordsCollection(uid)
            .whereEqualTo("deckId", deckId)
            .whereLessThanOrEqualTo("srs.nextReviewAt", now)
            .orderBy("srs.nextReviewAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if(error != null) { close(error); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toWordCard() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override suspend fun addWord(word: WordCard): Result<String> = runCatching {
        val col = wordsCollection(uid())
        val doc = col.document()
        doc.set(word.copy(id = doc.id).toMap()).await()
        doc.id
    }

    override suspend fun updateWord(word: WordCard): Result<Unit> = runCatching {
        wordsCollection(uid()).document(word.id).set(word.toMap()).await()
    }

    override suspend fun deleteWord(word: WordCard): Result<Unit> = runCatching {
        wordsCollection(uid()).document(word.id).delete().await()
    }
}