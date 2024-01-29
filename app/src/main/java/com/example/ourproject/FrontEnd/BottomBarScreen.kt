package com.example.ourproject.FrontEnd

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route:String,
    val title:String,
    val icon:ImageVector
){
    object Home: BottomBarScreen(
        route="home",
        title="Home",
        icon= Icons.Default.Home
    )
    object Donation: BottomBarScreen(
        route="donation",
        title="Donation",
        icon= Icons.Default.Home
    )
    object ShoppingAssistant: BottomBarScreen(
        route="shoppingAssistant",
        title="Shopping",
        icon= Icons.Default.ShoppingCart
    )
    object History: BottomBarScreen(
        route="history",
        title="History",
        icon= Icons.Default.ShoppingCart
    )
}
