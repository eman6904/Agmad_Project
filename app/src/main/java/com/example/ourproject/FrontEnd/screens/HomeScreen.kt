package com.example.ourproject.FrontEnd.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getOrganizations
import com.example.ourproject.BackEnd.Files.getRequests
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R


@Composable
fun homeScreen(navController: NavHostController) {

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
    requestsList = getRequests()

    Column() {

        var requestsNumber = rememberSaveable { mutableStateOf(0) }
        requestsNumber.value=requestsList.size
        HomeTopBar(requestsNumber,navController)
        Column() {

        }
    }
}

@Composable
fun HomeTopBar(requestNumber: MutableState<Int>,navController: NavHostController) {

    var showNotification = rememberSaveable { mutableStateOf(false) }
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
                            requestNumber.value = 0
                            navController.navigate(ScreensRoute.RequestsScreen.route)
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
                        IconButton(onClick = {}) {
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