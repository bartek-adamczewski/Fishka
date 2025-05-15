package com.example.fishka.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.fishka.ui.screens.CatchDetailScreen

sealed class Screen(val route: String) {
    data object FishList : Screen("fish_list")
    data object OtherList : Screen("other_list")
    data object FishDetail : Screen("fish_detail/{fishId}") {
        fun createRoute(fishId: Int) = "fish_detail/$fishId"
    }
    data object AddCatch: Screen("add_catch")
    object CatchDetail: Screen("catchDetail/{fishId}") {
        fun createRoute(fishId: Int) = "catchDetail/$fishId"
    }
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Screen.FishList.route,
        modifier = modifier
    ) {
        composable(Screen.FishList.route) {
            com.example.fishka.ui.screens.FishListScreen(navController)
        }
        composable(Screen.OtherList.route) {
            com.example.fishka.ui.screens.CatchListScreen(navController)
        }
        composable(
            route = Screen.FishDetail.route,
            arguments = listOf(navArgument("fishId") { type = NavType.IntType })
        ) { backStack ->
            val fishId = backStack.arguments?.getInt("fishId")!!
            com.example.fishka.ui.screens.FishDetailScreen(fishId, navController)
        }
        composable(Screen.AddCatch.route) {
            com.example.fishka.ui.screens.AddCatchScreen(navController)
        }
        composable(
            Screen.CatchDetail.route,
            arguments = listOf(navArgument("fishId") { type = NavType.IntType })
        ) { backStackEntry ->
            val fishId = backStackEntry.arguments!!.getInt("fishId")
            CatchDetailScreen(fishId, navController)
        }
    }
}