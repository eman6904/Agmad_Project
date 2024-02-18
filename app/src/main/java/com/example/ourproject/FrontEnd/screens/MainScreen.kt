package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.navigation.compose.rememberNavController
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.R
import com.example.ourproject.appNavGraph


@Composable
fun mainScreen(navController: NavHostController) {

    val navController = rememberNavController()
    var showBottomBar by rememberSaveable { mutableStateOf(false) }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    showBottomBar = when (navBackStackEntry?.destination?.route) {
        "signIn" -> false
        "signUp" -> false
        "registerAs"->false
        "faceScreen"->false
        "Or_Sign_Up"->false
        "foodContent"->false
        else -> true
    }
    Scaffold(
        bottomBar = {
            if(showBottomBar){
                val screens = listOf(
                    BottomBarScreen.Home,
                    BottomBarScreen.Donation,
                    BottomBarScreen.History,
                )
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
                    elevation = 20.dp
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
        }
    ) { appNavGraph(navController = navController) }
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
