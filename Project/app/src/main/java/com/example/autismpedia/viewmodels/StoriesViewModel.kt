package com.example.autismpedia.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.Prefs

class StoriesViewModel(private val repository: GameRepository, context: Context) : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    private val contextViewModel: Context = context

    private val _onAddImageToFirebase = MutableLiveData<Pair<Game, Int>>()
    val onAddImageToFirebase : LiveData<Pair<Game, Int>> = _onAddImageToFirebase

    fun onAddImageClicked(game: Game, imageNr: Int) {
        val prefs = Prefs(contextViewModel)
        if(prefs.adminEnabled) {
            _onAddImageToFirebase.value = Pair(game, imageNr)
        } else {

        }
    }

    fun onAddImageToFirebase(game: Game, fileName: String, fileUri: Uri) = repository.addImageIdToFirebase(game, fileName, fileUri)

    fun removeImageFromFirebaseStorage(game: Game, fileName: String) = repository.removeImageFromFirebaseStorage(game, fileName)
}