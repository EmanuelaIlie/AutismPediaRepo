package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.SingleLiveEvent

class GameIdeasViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onAddGameClicked = SingleLiveEvent<Void>()
    val onAddGameClicked : LiveData<Void> = _onAddGameClicked

    fun onAddGameClicked() {
        _onAddGameClicked.call()
    }

    fun onAddGameToFirebase(game: Game, gameType: GameType) = repository.addGame(game, gameType)

    fun getAllGames(gameType: GameType) = repository.getAllGames(gameType)
}