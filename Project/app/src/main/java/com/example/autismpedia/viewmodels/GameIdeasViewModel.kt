package com.example.autismpedia.viewmodels

import androidx.lifecycle.ViewModel
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.repositories.GameRepository

class GameIdeasViewModel(private val repository: GameRepository) : ViewModel() {

    fun getAllGames(gameType: GameType) = repository.getAllGames(gameType)
}