package com.example.fishka.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite

@Composable
fun BottomBar(navController: NavHostController) {
    val items = listOf(
        Triple(Screen.FishList, Icons.AutoMirrored.Filled.List, "Fish list"),
        Triple(Screen.OtherList, Icons.Filled.Favorite, "Your fishes")
    )


    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    NavigationBar {
        items.forEach { (screen, icon, description) ->
            NavigationBarItem(
                icon = { Icon(imageVector = icon, contentDescription = description) },
                label = { Text(screen.route) },
                selected = currentDestination?.route == screen.route,
                onClick = {
                    if (currentDestination?.route != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

