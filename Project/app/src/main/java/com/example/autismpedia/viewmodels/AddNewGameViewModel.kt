package com.example.autismpedia.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.SingleLiveEvent

class AddNewGameViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onAddNewGame = SingleLiveEvent<Void>()
    val onAddNewGame : LiveData<Void> = _onAddNewGame

    private val _onAddGameImage = SingleLiveEvent<Void>()
    val onAddGameImage : LiveData<Void> = _onAddGameImage

    fun onAddNewGame() {
        _onAddNewGame.call()
    }

    fun onAddGameImage() {
        _onAddGameImage.call()
    }

    fun onAddGameToFirebase(game: Game, gameType: GameType) = repository.addGame(game, gameType)

    fun onAddNewGameImageToStorage(game: Game, fileUri: Uri) = repository.addNewGameImageToStorage(game, fileUri)
}