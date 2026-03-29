package com.example.core_data.repository

import com.example.core_data.mapper.mapException
import com.example.core_data.mapper.toAppException
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

    override fun getWords(collectionId: String): Flow<List<WordCard>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e.toAppException()); return@callbackFlow}

        val listener = wordsCollection(uid)
            .whereEqualTo("collectionId", collectionId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error.toAppException()); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toWordCard() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override fun getWordsForReview(): Flow<List<WordCard>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e.toAppException()); return@callbackFlow }
        val now = System.currentTimeMillis()

        val listener = wordsCollection(uid)
            .whereLessThanOrEqualTo("srs.nextReviewAt", now)
            .orderBy("srs.nextReviewAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) { close(error.toAppException()); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toWordCard() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override fun getWordsForReview(collectionId: String): Flow<List<WordCard>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e.toAppException()); return@callbackFlow }
        val now = System.currentTimeMillis()

        val listener = wordsCollection(uid)
            .whereEqualTo("collectionId", collectionId)
            .whereLessThanOrEqualTo("srs.nextReviewAt", now)
            .orderBy("srs.nextReviewAt", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, error ->
                if(error != null) { close(error.toAppException()); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toWordCard() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override suspend fun addWord(word: WordCard): Result<String> = runCatching {
        val col = wordsCollection(uid())
        val doc = col.document()
        val synced = word.copy(id = doc.id, knowledgeLevel = word.srs.toKnowledgeLevel().name)

        doc.set(synced.toMap()).await()
        doc.id
    }.mapException()

    override suspend fun updateWord(word: WordCard): Result<Unit> = runCatching {
        val synced = word.copy(knowledgeLevel = word.srs.toKnowledgeLevel().name)

        wordsCollection(uid()).document(synced.id).set(synced.toMap()).await()
        Unit
    }.mapException()

    override suspend fun deleteWord(word: WordCard): Result<Unit> = runCatching {
        wordsCollection(uid()).document(word.id).delete().await()
        Unit
    }.mapException()
}