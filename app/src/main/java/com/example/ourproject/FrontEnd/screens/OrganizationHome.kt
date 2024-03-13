package com.example.ourproject.FrontEnd.screens

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getRequests
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R


@Composable
fun organizationHome(navController: NavHostController) {

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
    var requestsNumber = rememberSaveable { mutableStateOf(0) }
    requestsList = getRequests()

    Column() {

        requestsNumber.value=requestsList.size
        orHomeTopBar(requestsNumber,navController)
        Column() {

        }
    }
    val context= LocalContext.current
    BackHandler() {
        // Exit the app when the back button is pressed
        (context as? Activity)?.finish()
    }
}

@Composable
fun orHomeTopBar(requestNumber: MutableState<Int>, navController: NavHostController) {

    var showNotification = rememberSaveable { mutableStateOf(false) }
    var showMenu = rememberSaveable { mutableStateOf(false) }
        menuItems(navController,showMenu)
    val requests=stringResource(id = R.string.requests)

    showNotification.value = (requestNumber.value > 0)
    Card(
        modifier = Modifier
            .background(color = Color.White)
            .height(102.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(
            topEnd = 0.dp,
            topStart = 0.dp,
            bottomEnd = 30.dp,
            bottomStart = 30.dp
        ),
        backgroundColor = colorResource(id = R.color.mainColor),
        elevation = 15.dp
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.home), color = Color.White,
                        modifier = Modifier.padding(start=10.dp)) },
                    actions = {
                        IconButton(onClick = {
                            requestNumber.value = 0
                            navController.navigate(ScreensRoute.RequestsScreen.route+"/${requests}")
                        }) {
                            BadgedBox(badge = {
                                if (showNotification.value) {
                                    Badge {
                                        Text(
                                            text = requestNumber.value.toString(),
                                            color = Color.White
                                        )
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "notification icon",
                                    tint = Color.White
                                )
                            }
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search icon",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                                showMenu.value=!showMenu.value
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "menu icon",
                                tint = Color.White
                            )
                        }
                    },
                    backgroundColor = colorResource(id = R.color.mainColor),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        ) {}
    }
}
@Composable
fun menuItems(navController:NavHostController,showMenu:MutableState<Boolean>){


    val accepted_requests=stringResource(id = R.string.acceptedRequests)
    val rejected_requests=stringResource(id = R.string.rejectedRequests)

    if(showMenu.value){
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value=false },
            offset = DpOffset(x = (160).dp, y = (5).dp)
        )
        {
           DropdownMenuItem(
               onClick = {navController.navigate(ScreensRoute.RequestsScreen.route+"/${accepted_requests}")}
           ) {
               Row(){
                   Icon(
                       imageVector = Icons.Default.Done,
                       contentDescription = null,
                       tint = Color.Green
                   )
                   Text(
                       text = stringResource(R.string.acceptedRequests),
                       modifier = Modifier.padding(start=4.dp)
                   )
               }
           }
            DropdownMenuItem(
                onClick = {navController.navigate(ScreensRoute.RequestsScreen.route+"/${rejected_requests}") }
            ) {
                Row(){
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        tint = Color.Red
                    )
                    Text(
                        text = stringResource(R.string.rejectedRequests),
                        modifier = Modifier.padding(start=4.dp)
                    )
                }
            }
        }
    }
}