package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.R
import com.example.ourproject.bottomNavGraph

@Composable
fun mainScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            val screens = listOf(
                BottomBarScreen.Home,
                BottomBarScreen.Donation,
                BottomBarScreen.ShoppingAssistant,
                BottomBarScreen.History,
            )
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            Card(
                modifier = Modifier
                    .background(color = Color.White),
                shape = RoundedCornerShape(
                    topEnd = 20.dp,
                    topStart =20.dp,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
                ),
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 10.dp
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .background(Color.White)
                        .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                ) {
                    BottomNavigation(contentColor = colorResource(id = R.color.mainColor)) {
                        screens.forEach() {
                            addItem(it, currentDestination, navController)
                        }
                    }
                }
            }
        }
    ) {
        bottomNavGraph(navController = navController)
    }
}
@Composable
fun RowScope.addItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    if(screen== BottomBarScreen.History) {
        BottomNavigationItem(
            modifier = Modifier.background(Color.White),
            label = { Text(text = screen.title, color = Color.Black) },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.history_icon),
                    contentDescription = "navigation icon"
                )
            },
            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = { navController.navigate(screen.route) }
        )
    }else{
        BottomNavigationItem(
            modifier = Modifier.background(Color.White),
            label = { Text(text = screen.title, color = Color.Black) },
            icon = {
                Icon(screen.icon, contentDescription = "navigation icon")
            },
            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
            onClick = { navController.navigate(screen.route) }
        )
    }
}