package com.example.autismpedia.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.BaseApplication
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.utils.SingleLiveEvent

class StoriesViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onAddImageToFirebase = MutableLiveData<Pair<Game, Int>>()
    val onAddImageToFirebase : LiveData<Pair<Game, Int>> = _onAddImageToFirebase

    private val _onEnlargeImageEvent = SingleLiveEvent<Int>()
    val onEnlargeImageEvent : LiveData<Int> = _onEnlargeImageEvent

    private val _onPlaySoundClicked = SingleLiveEvent<Void>()
    val onPlaySoundClicked : LiveData<Void> = _onPlaySoundClicked

    private val _onAddNewSound = SingleLiveEvent<Void>()
    val onAddNewSound : LiveData<Void> = _onAddNewSound

    fun onAddImageClicked(game: Game, imageNr: Int) {
        val prefs = Prefs(BaseApplication.instance.applicationContext)
        if(prefs.adminEnabled) {
            _onAddImageToFirebase.value = Pair(game, imageNr)
        } else {
            _onEnlargeImageEvent.value = imageNr
        }
    }

    fun onPlaySoundClicked() {
        _onPlaySoundClicked.call()
    }

    fun onAddNewSound() {
        _onAddNewSound.call()
    }

    fun onAddImageToFirebase(game: Game, fileName: String, fileUri: Uri) = repository.addImageIdToFirebase(game, fileName, fileUri)

    fun onAddAudioToFirebase(game: Game, fileName: String, fileUri: Uri) = repository.addAudioToFirebase(game, fileName, fileUri)

    fun removeImageFromFirebaseStorage(game: Game, fileName: String) = repository.removeImageFromFirebaseStorage(game, fileName)
}