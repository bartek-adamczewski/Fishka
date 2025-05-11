package com.example.fishka.viewmodel.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishka.data.Fish
import com.example.fishka.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class FishDetailViewModel(
    private val fishId: Int
) : ViewModel() {
    private val _fish = mutableStateOf<Fish?>(null)
    val fish: State<Fish?> = _fish

    private var _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        loadFish()
    }

    private fun loadFish() {
        if (_isLoading.value) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.getSpeciesById(fishId).results.firstOrNull()
                _fish.value = response
            } catch (e: Exception) {
                Log.e("Fish Details", e.message.toString())
                _errorMessage.value = e.message
            } finally {
                Log.i("Fish Details", "Fish details fetched for fish id: $fishId")
                _isLoading.value = false
            }
        }
    }
}

