package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository

class StoriesViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onAddImageToStorage = MutableLiveData<Pair<Game, Int>>()
    val onAddImageToStorage : LiveData<Pair<Game, Int>> = _onAddImageToStorage

    fun onAddImageClicked(game: Game, imageNr: Int) {
        _onAddImageToStorage.value = Pair(game, imageNr)
    }

    fun onAddImageIdToFirestore(game: Game) = repository.addImageIdToFirestore(game)
}