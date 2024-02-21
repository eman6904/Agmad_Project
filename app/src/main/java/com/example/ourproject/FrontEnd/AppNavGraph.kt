package com.example.ourproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.FrontEnd.screens.*

@Composable
fun appNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ScreensRoute.FaceScreen.route

    ) {
        composable(route = ScreensRoute.SignUP.route) { DonorSignUp(navController) }
        composable(route = ScreensRoute.SignIn.route) { signIn(navController) }
        composable(route = ScreensRoute.FaceScreen.route) { face(navController) }
        composable(route = ScreensRoute.RegisterAs.route) { registerAs(navController) }
        composable(route = ScreensRoute.FoodContentImages.route) { foodContent(navController) }
        composable(route = ScreensRoute.OrganizationSignUp.route) { organizationSignUp(navController) }
        composable(route = BottomBarScreen.Home.route) { homeScreen(navController) }
        composable(route = BottomBarScreen.Donation.route) { donationScreen(navController) }
        composable(route = BottomBarScreen.History.route) { historyScreen(navController) }
        composable(route = ScreensRoute.RequestsScreen.route) { requests(navController) }

    }
}