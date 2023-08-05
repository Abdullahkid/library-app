package com.example.readerapp.navigation

enum class ReaderScreens {
    SplashScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    LoginScreen,
    SearchScreen,
    DetailScreen,
    ReaderStatsScreen,
    UpdateScreen,
    DescriptionScreen;

    companion object {
        fun fromRoute(route:String):ReaderScreens
        =when(route.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name ->DetailScreen
            ReaderStatsScreen.name ->ReaderStatsScreen
            UpdateScreen.name -> UpdateScreen
            null -> ReaderHomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}