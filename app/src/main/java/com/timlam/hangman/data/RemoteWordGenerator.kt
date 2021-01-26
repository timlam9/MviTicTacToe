package com.timlam.hangman.data

import com.google.firebase.firestore.FirebaseFirestore
import com.timlam.hangman.domain.WordGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class RemoteWordGenerator(private val firestoreDB: FirebaseFirestore) : WordGenerator {

    companion object {

        private const val HANGMAN_COLLECTION = "hangmanWords"

    }

    override suspend fun generateRandomWord(): State<String> = try {
        val snapshot = firestoreDB.collection(HANGMAN_COLLECTION).get().await()
        val words = snapshot.toObjects(FirebaseWord::class.java)
        State.success(words.map { it.value }.shuffled().first())
    } catch (e: Exception) {
        State.failed(e.localizedMessage ?: "")
    }

    override fun getList() = flow<State<List<String>>> {
        try {
            emit(State.loading())

            val snapshot = firestoreDB.collection(HANGMAN_COLLECTION).get().await()
            val words = snapshot.toObjects(FirebaseWord::class.java)

            emit(State.success(words.map { it.value }))
        } catch (e: Exception) {
            emit(State.failed(e.localizedMessage ?: ""))
        }
    }.flowOn(Dispatchers.IO)

}
