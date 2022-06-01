package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.utils.SingleLiveEvent

class HomeViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    private val _onLogoutClicked = SingleLiveEvent<Void>()
    val onLogoutClicked : LiveData<Void> = _onLogoutClicked

    fun onLogoutClicked() {
        _onLogoutClicked.call()
    }
}