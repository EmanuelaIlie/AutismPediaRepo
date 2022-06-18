package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.SingleLiveEvent

class GameIdeasViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onNavigateToNewGameFragment = SingleLiveEvent<Void>()
    val onNavigateToNewGameFragment : LiveData<Void> = _onNavigateToNewGameFragment

    fun onNavigateToNewGameFragment() {
        _onNavigateToNewGameFragment.call()
    }

    fun getAllGames(gameType: GameType) = repository.getAllGames(gameType)
}