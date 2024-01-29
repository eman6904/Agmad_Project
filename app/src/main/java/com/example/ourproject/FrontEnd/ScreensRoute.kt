package com.example.ourproject.FrontEnd

sealed class ScreensRoute (val route:String){
    object SignIn: ScreensRoute(route="signIn")
    object SignUP: ScreensRoute(route="signUp")
    object MainScreen: ScreensRoute(route="mainScreen")
}