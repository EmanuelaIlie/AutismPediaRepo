package com.example.autismpedia.viewmodelfactories

import android.app.Application
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.autismpedia.MainActivity
import com.example.autismpedia.repositories.GameRepository
import com.example.autismpedia.utils.Prefs
import com.example.autismpedia.viewmodels.StoriesViewModel

class StoriesViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(StoriesViewModel::class.java)) {
            return StoriesViewModel(repository = GameRepository()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}