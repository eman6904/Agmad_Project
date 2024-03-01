package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Files.myRequests
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R

@Composable
fun donorHome(navController: NavHostController){

    Column {

        val acceptedRequestedNumber= rememberSaveable{ mutableStateOf(0)}
        val rejectedRequestedNumber= rememberSaveable{ mutableStateOf(0)}

        val accList= myRequests(type = "Accepted")
        val rejList= myRequests(type = "Rejected")

        acceptedRequestedNumber.value=accList.size
        rejectedRequestedNumber.value=rejList.size

        doHomeTopBar(acceptedRequestedNumber,rejectedRequestedNumber,navController)
        Column() {

        }
    }
}
@Composable
fun doHomeTopBar(acceptedRequestedNumber: MutableState<Int>,rejectedRequestedNumber: MutableState<Int>, navController: NavHostController) {

    var showNotificationForAcc = rememberSaveable { mutableStateOf(false) }
    var showNotificationForRej = rememberSaveable { mutableStateOf(false) }
    var showMenu = rememberSaveable { mutableStateOf(false) }
    menuItems(navController,showMenu)
    showNotificationForAcc.value = (acceptedRequestedNumber.value > 0)
    showNotificationForRej.value = (rejectedRequestedNumber.value > 0)
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
                    title = { Text(text = stringResource(R.string.home), color = Color.White) },
                    navigationIcon = {
                        IconButton(
                            onClick = {}
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.arrowbackicon),
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            rejectedRequestedNumber.value = 0
                            navController.navigate(ScreensRoute.Response.route+"/Rejected Requests")
                        }) {
                            BadgedBox(badge = {
                                if (showNotificationForRej.value) {
                                    Badge(
                                        backgroundColor = Color.Red
                                    ){
                                        Text(
                                            text = rejectedRequestedNumber.value.toString(),
                                            color = Color.White
                                        )
                                    }
                                }}
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = "notification icon",
                                    tint = Color.White
                                )
                            }
                        }
                        IconButton(onClick = {
                            acceptedRequestedNumber.value = 0
                            navController.navigate(ScreensRoute.Response.route+"/Accepted Requests")
                        }) {
                            BadgedBox(badge = {
                                    if(showNotificationForAcc.value){
                                        Badge(
                                            backgroundColor = Color.Green
                                        ){
                                            Text(
                                                text = acceptedRequestedNumber.value.toString(),
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
