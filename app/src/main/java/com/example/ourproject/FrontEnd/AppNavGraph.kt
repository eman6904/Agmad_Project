package com.example.ourproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.FrontEnd.screens.*

@Composable
fun appNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = ScreensRoute.FaceScreen.route

    ) {
        composable(route = ScreensRoute.DonorSignUp.route) { DonorSignUp(navController) }
        composable(route = ScreensRoute.SignIn.route) { signIn(navController) }
        composable(route = ScreensRoute.FaceScreen.route) { face(navController) }
        composable(route = ScreensRoute.RegisterAs.route) { registerAs(navController) }
        composable(route = ScreensRoute.FoodContentImages.route+"/{imagesId}") {

            val arguments = requireNotNull(it.arguments)
            val imagesIdList = arguments.getString("imagesId")?.split(",") ?: emptyList()
            foodContent(navController,imagesIdList)
        }
        composable(route = ScreensRoute.OrganizationSignUp.route) { organizationSignUp(navController) }
        composable(route = BottomBarScreen.OrganizationHome.route) { organizationHome(navController) }
        composable(route = BottomBarScreen.Donation.route) { donationScreen(navController) }
        composable(route = BottomBarScreen.History.route) { historyScreen(navController) }
        composable(route = ScreensRoute.DonorHome.route) { donorHome(navController) }
        composable(route = ScreensRoute.RequestsScreen.route+"/{type}") {

            val type=it.arguments?.getString("type")
            requests(navController,type.toString())
        }
        composable(route = ScreensRoute.Response.route+"/{type}") {

            val type=it.arguments?.getString("type")
            response(navController,type.toString())
        }
        composable(route = ScreensRoute.RequestImages.route+ "/{id}/{requestType}") { it->

            val requestId=it.arguments?.getString("id")
            val requestType=it.arguments?.getString("requestType")
            requestImages(navController,requestId.toString(),requestType.toString())
        }
        composable(route = ScreensRoute.ResponseImages.route+ "/{id}/{requestType}") { it->

            val requestId=it.arguments?.getString("id")
            val requestType=it.arguments?.getString("requestType")
            responseImages(navController,requestId.toString(),requestType.toString())
        }
        composable(route = ScreensRoute.Levels.route) { levels(navController) }
        composable(route = ScreensRoute.Plant.route) { TreeAnimation(navController) }

    }
}