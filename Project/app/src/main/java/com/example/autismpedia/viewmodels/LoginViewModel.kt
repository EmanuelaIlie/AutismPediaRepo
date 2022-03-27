package com.example.autismpedia.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.autismpedia.utils.SingleLiveEvent

class LoginViewModel : ViewModel() {
    private val _onSignInClicked = SingleLiveEvent<Void>()
    val onSignInClicked : LiveData<Void> = _onSignInClicked

    fun onSignInClicked() {
        _onSignInClicked.call()
    }
}