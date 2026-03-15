package com.example.core_data.repository

import com.example.core_data.mapper.mapException
import com.example.core_data.mapper.toAppException
import com.example.core_data.mapper.toCollection
import com.example.core_data.mapper.toMap
import com.example.core_domain.exception.AppException
import com.example.core_domain.model.collection.Collection
import com.example.core_domain.repository.AuthRepository
import com.example.core_domain.repository.CollectionRepository
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
class CollectionRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val authRepository: AuthRepository
):  CollectionRepository {

    private suspend fun uid(): String = authRepository.currentUser.first()?.id
        ?: throw AppException.UserNotFound

    private fun decksCollection(uid: String) = firestore
        .collection("users")
        .document(uid)
        .collection("decks")

    override fun getCollections(): Flow<List<Collection>> = callbackFlow {
        val uid = try { uid() } catch (e: Exception) { close(e.toAppException()); return@callbackFlow }

        val listener = decksCollection(uid)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) { close(exception.toAppException()); return@addSnapshotListener }

                trySend(snapshot?.documents?.mapNotNull { it.toCollection() } ?: emptyList())
            }

        awaitClose { listener.remove() }
    }

    override suspend fun createCollection(collection: Collection): Result<String> = runCatching {
        val userCollection = decksCollection(uid())
        val newDocument = userCollection.document()
        newDocument.set(collection.copy(id = newDocument.id).toMap()).await()
        newDocument.id
    }.mapException()

    override suspend fun updateCollection(collection: Collection): Result<Unit> = runCatching {
        decksCollection(uid()).document(collection.id).set(collection.toMap()).await()
        Unit
    }.mapException()

    override suspend fun deleteCollection(collection: Collection): Result<Unit> = runCatching {
        decksCollection(uid()).document(collection.id).delete().await()
        Unit
    }.mapException()

}