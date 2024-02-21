package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getRequests
import com.example.ourproject.R
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun requests(navController:NavHostController){

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
    requestsList = getRequests()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        requestsTopBar(navController)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            items(items = requestsList, itemContent = { item ->
                requestItem(
                    name = item.donorName,
                    phone = item.donorPhone,
                    date_time = item.date_time
                )
            })
        }
    }
}
@Composable
fun requestItem(name:String,phone:String,date_time:String){


    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ){
            Image(
                painterResource(R.drawable.food_donation_icon2),
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                contentDescription = "",
            )
            Column (
                   modifier = Modifier
                       .weight(5f)
                       .padding(5.dp)
                    ){
                Text(
                    text=name,
                    modifier = Modifier
                )
                Text(
                    text=phone,
                    modifier = Modifier
                )
                Text(
                    text=date_time,
                    modifier = Modifier,
                    color= Color.Gray
                )

            }
        }
    }
}
@Composable
fun requestsTopBar(navController: NavHostController) {
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
                    title = {
                        Text(
                            text = stringResource(R.string.requests),
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.arrowbackicon),
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