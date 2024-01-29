package com.example.ourproject

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.FrontEnd.screens.*

@Composable
fun bottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ){
     composable(route= BottomBarScreen.Home.route){ homeScreen()}
     composable(route= BottomBarScreen.Donation.route){ donationScreen()}
     composable(route= BottomBarScreen.ShoppingAssistant.route){ shoppingScreen()}
     composable(route= BottomBarScreen.History.route){ historyScreen()}
    }
}