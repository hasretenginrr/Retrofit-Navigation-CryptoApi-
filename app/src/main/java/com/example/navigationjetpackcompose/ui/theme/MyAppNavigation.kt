package com.example.navigationjetpackcompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationjetpackcompose.Model.ProductModel
import com.example.navigationjetpackcompose.Routes
import com.example.navigationjetpackcompose.ScreenA
import com.example.navigationjetpackcompose.ScreenB
import com.google.gson.Gson

@Composable
fun MyAppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "screenA") {
        composable("screenA") {
            ScreenA(navController)
        }
        composable(
            route = "screenB/{productJson}",
            arguments = listOf(navArgument("productJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val productJson = backStackEntry.arguments?.getString("productJson")
            val product = productJson?.let { json ->
                Gson().fromJson(json, ProductModel::class.java)
            }
            ScreenB(product)
        }
    }
}
