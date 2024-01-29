package com.example.ourproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.FrontEnd.screens.*

@Composable
fun appNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.SignUP.route
    ){
        composable(route= ScreensRoute.SignUP.route){ signUp(navController) }
        composable(route= ScreensRoute.SignIn.route){ signIn(navController) }
        composable(route= ScreensRoute.MainScreen.route){ mainScreen(navController) }

    }
}