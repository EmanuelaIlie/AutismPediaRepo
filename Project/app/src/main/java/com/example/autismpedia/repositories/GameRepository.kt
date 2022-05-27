package com.example.autismpedia.repositories

import android.net.Uri
import com.example.autismpedia.enums.DailyActivitiesType
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.utils.Constants
import com.example.autismpedia.utils.State
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await

class GameRepository {
    private val mGameCollection = FirebaseFirestore.getInstance()

    fun getAllGames(gameType: GameType) = flow<State<List<Game>>>{
        emit(State.loading())
        val snapshot = mGameCollection.collection(gameType.string).get().await()
        val games = snapshot.toObjects(Game::class.java)

        emit(State.success(games))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addGame(game: Game, gameType: GameType) = flow<State<DocumentReference>> {
        emit(State.loading())
        val gameRef = mGameCollection.collection(game.type.toString()).add(game).await()

        emit(State.success(gameRef))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addImageIdToFirebase(game: Game, fileName: String, fileUri: Uri) = flow<State<DocumentReference>> {
        emit(State.loading())

        // add image file to storage
        val extension = ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("${game.type}/${Constants.FIRESTORE_STORAGE_IMAGES_FOLDER}/$fileName$extension")
        refStorage.putFile(fileUri).await()

        // add id to firestore
        mGameCollection.collection(game.type.toString()).document(game.id.toString()).update(Constants.FIRESTORE_STORAGE_IMAGES_FOLDER, game.images).await()

        emit(State.success(mGameCollection.collection(game.type.toString()).document(game.id.toString())))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun removeImageFromFirebaseStorage(game: Game, fileName: String) = flow<State<DocumentReference>> {
        emit(State.loading())

        // add image file to storage
        val extension = ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("${game.type}/${Constants.FIRESTORE_STORAGE_IMAGES_FOLDER}/$fileName$extension")
        refStorage.delete()

        emit(State.success(mGameCollection.collection(game.type.toString()).document(game.id.toString())))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun addDailyActivitiesTextToFirebase(game: Game, dailyActivitiesType: DailyActivitiesType) = flow<State<DocumentReference>> {
        emit(State.loading())

        when(dailyActivitiesType) {
            DailyActivitiesType.NECESSARY_OBJECTS -> {
                mGameCollection.collection(game.type.toString()).document(game.id.toString()).update(Constants.FIRESTORE_NECESSARY_OBJECTS_FILED, game.necessary_objects).await()
            }
            DailyActivitiesType.STEPS -> {
                mGameCollection.collection(game.type.toString()).document(game.id.toString()).update(Constants.FIRESTORE_STEPS_FILED, game.steps).await()
            }
        }

        emit(State.success(mGameCollection.collection(game.type.toString()).document(game.id.toString())))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getDailyActivitiesTextFromFirebase(game: Game) = flow<State<Game?>> {
        emit(State.loading())

        val snapshot = mGameCollection.collection(game.type.toString()).document(game.id.toString()).get().await()
        val firestoreGame = snapshot.toObject(Game::class.java)

        emit(State.success(firestoreGame))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    fun getDidacticGameFromFirebase(game: Game) = flow<State<Game?>> {
        emit(State.loading())

        val snapshot = mGameCollection.collection(game.type.toString()).document(game.id.toString()).get().await()
        val firestoreGame = snapshot.toObject(Game::class.java)

        emit(State.success(firestoreGame))
    }.catch {
        emit(State.failed(it.message.toString()))
    }.flowOn(Dispatchers.IO)
}