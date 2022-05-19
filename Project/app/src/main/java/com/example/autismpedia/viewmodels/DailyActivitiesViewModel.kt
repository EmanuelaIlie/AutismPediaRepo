package com.example.autismpedia.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DailyActivitiesViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onSaveNecessaryObjectsClicked = MutableSharedFlow<Unit>(replay = 0)
    val onSaveNecessaryObjectsClicked = _onSaveNecessaryObjectsClicked.asSharedFlow()

    fun onSaveNecessaryObjectsClicked() {
        viewModelScope.launch {
            _onSaveNecessaryObjectsClicked.emit(Unit)
        }
    }

    fun onAddNecessaryObjectsToFirebase(game: Game) = repository.addNecessaryObjectsToFirebase(game)

    fun onGetNecessaryObjectsToFirebase(game: Game) = repository.getNecessaryObjectsToFirebase(game)
}