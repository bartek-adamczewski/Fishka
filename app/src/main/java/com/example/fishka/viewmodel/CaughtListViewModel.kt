package com.example.fishka.viewmodel

import com.example.fishka.database.entity.CaughtFish
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishka.database.CaughtFishDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CaughtListViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = CaughtFishDatabase.getInstance(application).caughtFishDao()

    val catches: StateFlow<List<CaughtFish>> = dao.getAllCatches()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun insertCaughtFish(caughtFish: CaughtFish) = viewModelScope.launch {
        dao.insert(caughtFish)
    }

    fun deleteCaughtFish(fish: CaughtFish) = viewModelScope.launch {
        dao.delete(fish)
    }

    fun getCatchById(id: Int): Flow<CaughtFish?> {
        return dao.getCatchById(id)
    }
}