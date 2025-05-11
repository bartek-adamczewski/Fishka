package com.example.fishka.viewmodel

import android.util.Log
import com.example.fishka.retrofit.RetrofitClient
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fishka.data.Fish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class FishListViewModel : ViewModel() {
    private val _fishList = mutableStateOf<List<Fish>>(emptyList())
    val fishList: State<List<Fish>> = _fishList

    val searchQuery = MutableStateFlow("")

    private var currentPage = 1
    private var isLoading = false
    private var endReached = false

    init {
        observeSearch()
        loadFish()
    }

    private fun observeSearch() {
        viewModelScope.launch {
            searchQuery
                .debounce(800)
                .distinctUntilChanged()
                .collectLatest { query ->
                    currentPage = 1
                    endReached = false
                    _fishList.value = emptyList()
                    loadFish()
                }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery.value = newQuery
    }

    fun loadFish() {
        if (isLoading || endReached) return

        isLoading = true
        viewModelScope.launch {
            try {
                val query = searchQuery.value

                val response = if (query.isNotBlank()) {
                    RetrofitClient.api.autocompleteSpecies(query = query)
                } else {
                    RetrofitClient.api.getAllSpecies(
                        page = currentPage,
                        query = null
                    )
                }

                val newFish = response.results

                if (newFish.isEmpty()) {
                    endReached = true
                } else {
                    _fishList.value += newFish
                    if (query.isBlank()) currentPage++
                }
            } catch (e: Exception) {
                Log.e("Fish List", "Error while fetching data from API", e)
            } finally {
                isLoading = false
            }
        }
    }
}

