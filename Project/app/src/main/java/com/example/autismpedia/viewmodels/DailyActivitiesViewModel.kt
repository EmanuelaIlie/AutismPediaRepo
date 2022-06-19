package com.example.autismpedia.viewmodels

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autismpedia.enums.DailyActivitiesType
import com.example.autismpedia.models.Game
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.SingleLiveEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class DailyActivitiesViewModel(private val repository: GameRepository) : ViewModel() {

    private val _onSaveTextClicked = MutableSharedFlow<DailyActivitiesType>(replay = 0)
    val onSaveTextClicked = _onSaveTextClicked.asSharedFlow()

    private val _onAddNewVideoEvent = SingleLiveEvent<Void>()
    val onAddNewVideoEvent : LiveData<Void> = _onAddNewVideoEvent

    fun onSaveTextClicked(dailyActivitiesType: DailyActivitiesType) {
        viewModelScope.launch {
            _onSaveTextClicked.emit(dailyActivitiesType)
        }
    }

    fun onAddNewVideoEvent() {
        _onAddNewVideoEvent.call()
    }

    fun onAddDailyActivitiesTextToFirebase(game: Game, dailyActivitiesType: DailyActivitiesType) = repository.addDailyActivitiesTextToFirebase(game, dailyActivitiesType)

    fun onGetDailyActivitiesTextFromFirebase(game: Game) = repository.getDailyActivitiesTextFromFirebase(game)

    fun onAddVideoToFirebase(game: Game, fileName: String, fileUri: Uri) = repository.addVideoToFirebase(game, fileName, fileUri)

}