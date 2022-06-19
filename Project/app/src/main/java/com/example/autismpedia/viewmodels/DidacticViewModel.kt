package com.example.autismpedia.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.models.Game
import com.example.autismpedia.models.Minigame
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.SingleLiveEvent

class DidacticViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onNextMinigame = SingleLiveEvent<Void>()
    val onNextMinigame : LiveData<Void> = _onNextMinigame

    private val _onAnswerImageClicked = MutableLiveData<Int>()
    val onAnswerImageClicked : LiveData<Int> = _onAnswerImageClicked

    private val _onAddNewImageEvent = SingleLiveEvent<Int>()
    val onAddNewImageEvent : LiveData<Int> = _onAddNewImageEvent

    fun getAllMinigames(game: Game) = repository.getDidacticMinigamesFromFirebase(game)

    fun onNextMinigame() {
        _onNextMinigame.call()
    }

    fun onAnswerImageClicked(imageIndex: Int) {
        _onAnswerImageClicked.value = imageIndex
    }

    fun onAddImageToFirebase(game: Game, fileName: String, fileUri: Uri, currentMinigame: Minigame) = repository.addMinigameImageToFirebase(game, fileName, fileUri, currentMinigame)

}