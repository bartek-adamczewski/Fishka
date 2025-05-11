package com.example.fishka.ui.screens

import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.fishka.navigation.Screen
import com.example.fishka.viewmodel.FishListViewModel

@Composable
fun FishListScreen(navController: NavHostController) {
    val viewModel = viewModel<FishListViewModel>()
    val fishList = viewModel.fishList.value
    val query by viewModel.searchQuery.collectAsState()

    Column(Modifier.fillMaxSize()) {
        TextField(
            value = query,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            label = { Text("Szukaj ryby...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(fishList) { index, fish ->
                if (index >= fishList.size - 1 && query.isBlank()) {
                    viewModel.loadFish()
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(Screen.FishDetail.createRoute(fish.id))
                        }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        AsyncImage(
                            model = fish.defaultPhoto?.mediumUrl,
                            contentDescription = fish.commonName ?: fish.scientificName,
                            modifier = Modifier.size(64.dp)
                        )
                        Column {
                            Text(text = fish.commonName ?: fish.scientificName)
                            Text(text = fish.scientificName)
                        }
                    }
                }
            }
        }
    }
}


