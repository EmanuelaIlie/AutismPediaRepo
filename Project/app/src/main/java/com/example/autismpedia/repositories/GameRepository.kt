package com.example.autismpedia.repositories

import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.Constants
import com.example.autismpedia.utils.State
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class GameRepository {
    private val mGameCollection = FirebaseFirestore.getInstance()

    fun getAllGames(gameType: GameType) = flow<State<List<Game>>>{

        // Emit loading state
        emit(State.loading())

        val snapshot = when(gameType) {
            GameType.STORY -> {
                mGameCollection.collection(Constants.FIRESTORE_STORIES_COLLECTION).get().await()
            }
            GameType.DIDACTIC -> {
                mGameCollection.collection(Constants.FIRESTORE_DIDACTIC_COLLECTION).get().await()
            }
            GameType.DAILY_ACTIVITIES -> {
                mGameCollection.collection(Constants.FIRESTORE_DAILY_ACTIVITIES_COLLECTION).get().await()
            }
        }
        val games = snapshot.toObjects(Game::class.java)

        // Emit success state with data
        emit(State.success(games))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addGame(Game: Game, gameType: GameType) = flow<State<DocumentReference>> {

        // Emit loading state
        emit(State.loading())

        val GameRef = when(gameType) {
            GameType.STORY -> {
                mGameCollection.collection(Constants.FIRESTORE_STORIES_COLLECTION).add(Game).await()
            }
            GameType.DIDACTIC -> {
                mGameCollection.collection(Constants.FIRESTORE_DIDACTIC_COLLECTION).add(Game).await()
            }
            GameType.DAILY_ACTIVITIES -> {
                mGameCollection.collection(Constants.FIRESTORE_DAILY_ACTIVITIES_COLLECTION).add(Game).await()
            }
        }

        // Emit success state with Game reference
        emit(State.success(GameRef))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}