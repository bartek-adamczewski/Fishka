package com.example.fishka.ui.screens

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.fishka.entity.CaughtFish
import com.example.fishka.viewmodel.CaughtListViewModel
import java.io.File

@Composable
fun AddCatchScreen(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: CaughtListViewModel = viewModel()

    var name by remember { mutableStateOf(TextFieldValue()) }
    var sizeCm by remember { mutableStateOf(TextFieldValue()) }
    var weightKg by remember { mutableStateOf(TextFieldValue()) }
    var note by remember { mutableStateOf(TextFieldValue()) }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> photoUri = uri }

    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa zwyczajowa") }
        )
        OutlinedTextField(
            value = sizeCm,
            onValueChange = { sizeCm = it },
            label = { Text("Rozmiar (cm)") },
            singleLine = true
        )
        OutlinedTextField(
            value = weightKg,
            onValueChange = { weightKg = it },
            label = { Text("Waga (kg)") }
        )
        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Opis") }
        )

        Button(onClick = { launcher.launch("image/*") }) {
            Text("Wybierz zdjęcie")
        }

        photoUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                val savedPath = photoUri?.let { copyImageToInternalStorage(context, it) }

                val catch = CaughtFish(
                    name = name.text,
                    size = sizeCm.text.toFloatOrNull() ?: 0f,
                    weight = weightKg.text.toFloatOrNull() ?: 0f,
                    note = note.text.ifBlank { null },
                    photoUri = savedPath
                )
                viewModel.insertCaughtFish(catch)
                navController.popBackStack()
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("Zapisz")
        }
    }
}

fun copyImageToInternalStorage(context: Context, uri: Uri): String? {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        val fileName = "catch_${System.currentTimeMillis()}.jpg"
        val file = File(context.filesDir, fileName)
        inputStream?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        file.absolutePath
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
