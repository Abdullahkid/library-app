package com.example.readerapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.readerapp.screens.ReaderSplashScreen
import com.example.readerapp.screens.description.Description
import com.example.readerapp.screens.details.BookDetailsScreen
import com.example.readerapp.screens.home.HomeViewModel
import com.example.readerapp.screens.home.ReaderHomeScreen
import com.example.readerapp.screens.login.ReaderLoginScreen
import com.example.readerapp.screens.search.BookViewModel
import com.example.readerapp.screens.search.SearchScreen
import com.example.readerapp.screens.stats.ReaderStatsScreen
import com.example.readerapp.screens.update.BookUpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ReaderScreens.SplashScreen.name
    ){
        composable(ReaderScreens.SplashScreen.name){
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderHomeScreen.name){
            val homeViewModel = hiltViewModel<HomeViewModel>()
            ReaderHomeScreen(navController = navController,homeViewModel = homeViewModel)
        }
        composable(ReaderScreens.SearchScreen.name){
            val searchViewModel = hiltViewModel<BookViewModel>()
            SearchScreen(navController = navController, viewModel = searchViewModel)
        }
        composable(ReaderScreens.LoginScreen.name){
            ReaderLoginScreen(navController = navController)
        }
        composable(ReaderScreens.ReaderStatsScreen.name){
            ReaderStatsScreen(navController = navController, viewModel = hiltViewModel<HomeViewModel>())
        }

        val route = ReaderScreens.DetailScreen.name
        composable(
            route = "$route/{bookId}",
            arguments = listOf(
                navArgument("bookId"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(
                    navController = navController,
                    bookId = it.toString()
                )
            }
        }

        val update = ReaderScreens.UpdateScreen.name
        composable(
            route = "$update/{bookItemId}",
            arguments = listOf(
                navArgument(name = "bookItemId"){
                    type = NavType.StringType
                }
            )
        ){backStackEntry->
            backStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(
                    navController = navController,
                    bookItemId = it.toString()
                )
            }
        }

        composable(ReaderScreens.DescriptionScreen.name){
            Description(
                navController = navController,
                //homeViewModel = homeViewModel
            )
        }
    }
}


