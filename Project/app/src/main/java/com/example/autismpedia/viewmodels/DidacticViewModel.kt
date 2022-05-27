package com.example.autismpedia.viewmodels

import androidx.lifecycle.ViewModel
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository

class DidacticViewModel(private val repository: GameRepository) : ViewModel() {

    fun getAllMinigames(game: Game) = repository.getDidacticMinigamesFromFirebase(game)
}