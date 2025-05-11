package com.example.fishka.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.fishka.viewmodel.CaughtListViewModel
import com.example.fishka.navigation.Screen

@Composable
fun CatchListScreen(navController: NavHostController) {
    val viewModel: CaughtListViewModel = viewModel()
    val catches = viewModel.catches.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(catches.value) { c ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        Modifier
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        c.photoUri?.let { uri ->
                            AsyncImage(
                                model = uri,
                                contentDescription = c.name,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = c.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        IconButton(onClick = { viewModel.deleteCaughtFish(c) }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Usuń"
                            )
                        }
                    }
                }
            }
        }

        Button(
            onClick = { navController.navigate(Screen.AddCatch.route) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Dodaj rybę")
        }
    }
}

