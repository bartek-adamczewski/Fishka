package com.example.fishka.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.fishka.data.Fish
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import com.example.fishka.viewmodel.details.FishDetailViewModel
import com.example.fishka.viewmodel.details.FishDetailViewModelFactory
import androidx.core.net.toUri

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FishDetailScreen(fishId: Int, navController: NavHostController) {
    val viewModel: FishDetailViewModel = viewModel(
        factory = FishDetailViewModelFactory(fishId)
    )
    val fish by viewModel.fish
    val isLoading by viewModel.isLoading
    val error by viewModel.errorMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = fish?.commonName ?: "Szczegóły ryby") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Powrót"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
                error != null -> {
                    Text(
                        text = "Coś poszło nie tak:\n$error",
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
                fish != null -> {
                    FishDetailContent(fish = fish!!)
                }
            }
        }
    }
}

@Composable
fun FishDetailContent(fish: Fish) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = fish.defaultPhoto?.mediumUrl,
            contentDescription = fish.commonName ?: fish.scientificName,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        Column(Modifier.padding(horizontal = 16.dp)) {
            fish.commonName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
            Text(
                text = fish.scientificName,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic
            )
        }

        Spacer(Modifier.height(24.dp))

        Column(
            Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            InfoRow("Liczba obserwacji:", fish.observationsCount?.toString() ?: "brak danych")

            fish.extinct?.let {
                InfoRow("Czy gatunek wymarły:", if (it) "Tak" else "Nie")
            }

            fish.exactMatch?.let {
                InfoRow("Dokładne dopasowanie:", if (it) "Tak" else "Nie")
            }

            val context = LocalContext.current

            fish.wikipediaUrl?.let { url ->
                ClickableInfoRow(
                    label = "Wikipedia:",
                    value = url,
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                        context.startActivity(intent)
                    }
                )
            }
        }
        Spacer(Modifier.height(32.dp))
    }
}


@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun ClickableInfoRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        )
    }
}

