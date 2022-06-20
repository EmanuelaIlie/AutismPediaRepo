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

    private val _onAddNewImageEvent = SingleLiveEvent<Pair<Game, Int>>()
    val onAddNewImageEvent : LiveData<Pair<Game, Int>> = _onAddNewImageEvent

    private val _onAddNewMinigameEvent = SingleLiveEvent<Void>()
    val onAddNewMinigameEvent : LiveData<Void> = _onAddNewMinigameEvent

    fun getAllMinigames(game: Game) = repository.getDidacticMinigamesFromFirebase(game)

    fun onNextMinigame() {
        _onNextMinigame.call()
    }

    fun onAnswerImageClicked(imageIndex: Int) {
        _onAnswerImageClicked.value = imageIndex
    }

    fun onAddNewImageEvent(imageNr: Int, game: Game) {
        _onAddNewImageEvent.value = Pair(game, imageNr)
    }

    fun onAddNewMinigameEvent() {
        _onAddNewMinigameEvent.call()
    }

    fun onAddImageToFirebase(game: Game, fileName: String, fileUri: Uri, currentMinigame: Minigame) = repository.addMinigameImageToFirebase(game, fileName, fileUri, currentMinigame)

    fun onAddMinigameToFirebase(game: Game) = repository.addMinigame(game)
}