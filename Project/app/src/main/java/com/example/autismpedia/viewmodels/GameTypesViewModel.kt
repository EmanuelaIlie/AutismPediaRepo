package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.utils.SingleLiveEvent

class GameTypesViewModel : ViewModel() {
    // TODO: Implement the ViewModel

     private val _onNavigateToGameIdeas=SingleLiveEvent<Void>()
     val onNavigateToGameIdeas: LiveData<Void> =_onNavigateToGameIdeas

    fun onNavigateToGameIdeas(){
        _onNavigateToGameIdeas.call()
    }
}