package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.models.Game

class StoriesViewModel : ViewModel() {

    private val _onAddImage = MutableLiveData<Pair<Game, Int>>()
    val onAddImage : LiveData<Pair<Game, Int>> = _onAddImage

    fun onAddImageClicked(game: Game, imageNr: Int) {
        _onAddImage.value = Pair(game, imageNr)
    }
}