package com.example.autismpedia.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autismpedia.enums.DailyActivitiesType
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DailyActivitiesViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onSaveTextClicked = MutableSharedFlow<DailyActivitiesType>(replay = 0)
    val onSaveTextClicked = _onSaveTextClicked.asSharedFlow()

    fun onSaveTextClicked(dailyActivitiesType: DailyActivitiesType) {
        viewModelScope.launch {
            _onSaveTextClicked.emit(dailyActivitiesType)
        }
    }

    fun onAddDailyActivitiesTextToFirebase(game: Game, dailyActivitiesType: DailyActivitiesType) = repository.addDailyActivitiesTextToFirebase(game, dailyActivitiesType)

    fun onGetDailyActivitiesTextFromFirebase(game: Game) = repository.getDailyActivitiesTextFromFirebase(game)
}