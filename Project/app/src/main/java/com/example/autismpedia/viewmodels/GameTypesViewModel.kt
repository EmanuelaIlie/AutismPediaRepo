package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.enums.GameType
import com.example.autismpedia.utils.SingleLiveEvent

class GameTypesViewModel : ViewModel() {

     private val _onNavigateToGameIdeas=SingleLiveEvent<GameType>()
     val onNavigateToGameIdeas: LiveData<GameType> =_onNavigateToGameIdeas

    fun onNavigateToGameIdeas(gameType: GameType){
        _onNavigateToGameIdeas.value = gameType
    }
}