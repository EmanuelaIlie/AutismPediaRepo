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

        val snapshot = mGameCollection.collection(gameType.string).get().await()

        val games = snapshot.toObjects(Game::class.java)

        // Emit success state with data
        emit(State.success(games))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addGame(game: Game, gameType: GameType) = flow<State<DocumentReference>> {

        // Emit loading state
        emit(State.loading())

        val gameRef = mGameCollection.collection(game.type.toString()).add(game).await()

        // Emit success state with Game reference
        emit(State.success(gameRef))
    }.catch {
        // If exception is thrown, emit failed state along with message.
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}