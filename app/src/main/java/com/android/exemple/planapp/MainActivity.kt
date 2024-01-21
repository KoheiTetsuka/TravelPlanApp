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
import com.android.exemple.planapp.ui.screen.DetailEditScreen
import com.android.exemple.planapp.ui.screen.DetailScreen
import com.android.exemple.planapp.ui.screen.PlanCreateScreen
import com.android.exemple.planapp.ui.screen.PlanEditScreen
import com.android.exemple.planapp.ui.screen.PlanScreen
import com.android.exemple.planapp.ui.screen.PropertyCreateScreen
import com.android.exemple.planapp.ui.screen.PropertyEditScreen
import com.android.exemple.planapp.ui.screen.PropertyScreen
import com.android.exemple.planapp.ui.theme.PlanAppTheme
import com.android.exemple.planapp.ui.viewmodel.DetailViewModel
import com.android.exemple.planapp.ui.viewmodel.PlanViewModel
import com.android.exemple.planapp.ui.viewmodel.PropertyViewModel
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
                            route = "planEdit/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<PlanViewModel>()
                            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
                            PlanEditScreen(
                                navController = navController,
                                viewModel = viewModel,
                                planId = planId
                            )
                        }
                        composable(
                            route = "detailEdit/{detailId}",
                            arguments = listOf(navArgument("detailId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<DetailViewModel>()
                            val detailId = backStackEntry.arguments?.getInt("detailId") ?: 0
                            DetailEditScreen(
                                navController = navController,
                                viewModel = viewModel,
                                detailId = detailId
                            )
                        }

                        composable(
                            route = "property/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<PropertyViewModel>()
                            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
                            PropertyScreen(
                                navController = navController,
                                viewModel = viewModel,
                                planId = planId
                            )
                        }
                        composable(
                            route = "propertyCreate/{planId}",
                            arguments = listOf(navArgument("planId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<PropertyViewModel>()
                            val planId = backStackEntry.arguments?.getInt("planId") ?: 0
                            PropertyCreateScreen(
                                navController = navController,
                                viewModel = viewModel,
                                planId = planId
                            )
                        }
                        composable(
                            route = "propertyEdit/{propertyId}",
                            arguments = listOf(navArgument("propertyId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val viewModel = hiltViewModel<PropertyViewModel>()
                            val propertyId = backStackEntry.arguments?.getInt("propertyId") ?: 0
                            PropertyEditScreen(
                                navController = navController,
                                viewModel = viewModel,
                                propertyId = propertyId
                            )
                        }
                    }
                }
            }
        }
    }
}






