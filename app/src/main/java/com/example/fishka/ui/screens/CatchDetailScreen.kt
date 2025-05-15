package com.example.fishka.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.fishka.viewmodel.CaughtListViewModel
import java.util.Date
import java.util.Locale

@Composable
fun CatchDetailScreen(
    fishId: Int,
    navController: NavHostController,
    viewModel: CaughtListViewModel = viewModel()
) {
    val fish = viewModel.getCatchById(fishId).collectAsState(initial = null).value

    fish.let { c ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            c?.photoUri?.let { uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = c.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Text(text = "Nazwa: ${c?.name}", style = MaterialTheme.typography.titleLarge)
            Text(text = "Długość: ${c?.size} cm")
            Text(text = "Waga: ${c?.weight} kg")
            Text(text = "Notatka: ${c?.note.orEmpty()}")
            c?.timestamp?.let {
                Text(
                    text = "Złowiona: ${
                        java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                            .format(Date(c.timestamp))
                    }"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = { navController.popBackStack() }) {
                Text("Powrót")
            }
        }
    }
}
