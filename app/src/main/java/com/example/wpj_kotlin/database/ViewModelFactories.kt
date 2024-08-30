package com.example.wpj_kotlin.database

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wpj_kotlin.viewModels.NewItemViewModel

class NewItemViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewItemViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}