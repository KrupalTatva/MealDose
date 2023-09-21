package com.restro.mealdose

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.restro.mealdose.screen.DashboardScreen
import com.restro.mealdose.screen.MealDetailScreen
import com.restro.mealdose.screen.MealListScreen
import com.restro.mealdose.screen.RandomScreen

class Routes {
    companion object {
        const val DashboardScreen = "DashboardScreen"
        const val RandomScreen = "RandomScreen"
        const val MealListScreen = "MealListScreen/{${MealListRoute.PARAM}}"
        const val MealScreen = "MealDetailScreen/{${MealRoute.PARAM}}"
    }
}

object MealRoute {
    const val PARAM = "id"

    val navigate = listOf(
        navArgument(PARAM) {
            type = NavType.StringType
        }
    )

    fun createRoute(id: String): String {
        return Routes.MealScreen.replace("{${PARAM}}", id)
    }

    fun getArgument(argument: Bundle?): String {
        return try {
            argument?.getString(PARAM) ?: ""
        } catch (e: Exception) {
            ""
        }
    }
}

object MealListRoute {
    const val PARAM = "categoryName"

    val navigate = listOf(
        navArgument(PARAM) {
            type = NavType.StringType
        }
    )

    fun createRoute(categoryName: String): String {
        return Routes.MealListScreen.replace("{${PARAM}}", categoryName)
    }
}


@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.DashboardScreen) {
        composable(Routes.DashboardScreen) {
            DashboardScreen(
                navigateToRandomScreen = {
                    navController.navigate(Routes.RandomScreen)
                },
                navigateToMealListScreen = { categoryName ->
                    navController.navigate(MealListRoute.createRoute(categoryName))
                }
            )
        }

        composable(Routes.RandomScreen) {
            RandomScreen(
                onBack = {
                    navController.popBackStack()
                },
            )
        }

        composable(Routes.MealListScreen, arguments = MealListRoute.navigate) {
            MealListScreen(
                onBack = {
                    navController.popBackStack()
                },
                navigationToMealDetailScreen = { id ->
                    navController.navigate(MealRoute.createRoute(id))
                }
            )
        }

        composable(Routes.MealScreen, arguments = MealRoute.navigate) {
            MealDetailScreen(
                mealId = MealRoute.getArgument(it.arguments),
                onBack = {
                    navController.popBackStack()
                },
            )
        }

    }
}