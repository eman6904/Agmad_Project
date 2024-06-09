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
    object OrganizationHome: BottomBarScreen(
        route="home",
        title="Home",
        icon= Icons.Default.Home
    )
    object Donation: BottomBarScreen(
        route="donation",
        title="Donation",
        icon= Icons.Default.Home
    )
    object DonorHistory: BottomBarScreen(
        route="history",
        title="History",
        icon= Icons.Default.ShoppingCart
    )
}
