package com.android.exemple.planapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.android.exemple.planapp.ui.screen.DetailCreateScreen
import com.android.exemple.planapp.ui.screen.DetailScreen
import com.android.exemple.planapp.ui.screen.EditScreen
import com.android.exemple.planapp.ui.screen.PlanCreateScreen
import com.android.exemple.planapp.ui.screen.PlanScreen
import com.android.exemple.planapp.ui.screen.PropertyCreateScreen
import com.android.exemple.planapp.ui.screen.PropertyScreen
import com.android.exemple.planapp.ui.theme.PlanAppTheme
import com.android.exemple.planapp.ui.viewModel.DetailViewModel
import com.android.exemple.planapp.ui.viewModel.EditViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "home") {
                        composable(route = "home") {
                            PlanScreen(navController = navController)
                        }
                        composable(route = "planCreate") {
                            PlanCreateScreen(navController = navController)
                        }
                        composable(
                            route = "detail/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<DetailViewModel>()
                            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
                            DetailScreen(
                                navController = navController,
                                viewModel = viewModel,
                                planId = planId
                            )
                        }
                        composable(
                            route = "detailCreate/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<DetailViewModel>()
                            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
                            DetailCreateScreen(
                                navController = navController,
                                viewModel = viewModel,
                                planId = planId
                            )
                        }
                        composable(
                            route = "edit/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<EditViewModel>()
                            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
                            EditScreen(
                                navController = navController,
                                viewModel = viewModel,
                                planId = planId
                            )
                        }
                        composable(
                            route = "property"
                        ) {
                            PropertyScreen(navController = navController)
                        }
                        composable(
                            route = "propertyCreate"
                        ) {
                            PropertyCreateScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}






