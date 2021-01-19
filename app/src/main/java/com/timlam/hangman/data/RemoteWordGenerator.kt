package com.timlam.hangman.data

import com.google.firebase.firestore.FirebaseFirestore
import com.timlam.hangman.domain.WordGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RemoteWordGenerator(private val firestoreDB: FirebaseFirestore) : WordGenerator<String> {

    companion object {

        private const val HANGMAN_COLLECTION = "hangmanWords"

    }

    override suspend fun generateRandomWord(): String {
        val snapshot = firestoreDB.collection(HANGMAN_COLLECTION).get().await()
        val words = snapshot.toObjects(FirebaseWord::class.java)
        return (words.map { it.value }.shuffled().first())
    }

    override fun getList(): Flow<List<String>> = flow {
        val snapshot = firestoreDB.collection(HANGMAN_COLLECTION).get().await()
        val words = snapshot.toObjects(FirebaseWord::class.java)
        emit(words.map { it.value })
    }.flowOn(Dispatchers.IO)

}
