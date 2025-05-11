package com.example.fishka.viewmodel.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FishDetailViewModelFactory(
    private val fishId: Int
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FishDetailViewModel(fishId) as T
    }
}