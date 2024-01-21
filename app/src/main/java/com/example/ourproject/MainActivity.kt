package com.example.ourproject

import android.os.Bundle
import android.widget.Spinner
import androidx.activity.ComponentActivity
import androidx.activity.R
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Magenta
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ourproject.screens.signUp
import com.example.ourproject.ui.theme.OurProjectTheme
import java.time.Clock.offset

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            signUp()
           // mainScreen()
        }
    }
}

@Composable
fun mainScreen() {
    val navController = rememberNavController()
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
                    BottomNavigation(contentColor = colorResource(id = com.example.ourproject.R.color.mainColor)) {
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
    if(screen==BottomBarScreen.History) {
        BottomNavigationItem(
            modifier = Modifier.background(Color.White),
            label = { Text(text = screen.title, color = Color.Black) },
            icon = {
                Icon(
                    painter = painterResource(id = com.example.ourproject.R.drawable.history_icon),
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