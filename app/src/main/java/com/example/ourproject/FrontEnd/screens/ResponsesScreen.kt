package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.ourproject.BackEnd.Files.myRequests
import com.example.ourproject.R

@Composable
fun response(navController: NavHostController, responseType:String){

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }

    val accepted_requests=stringResource(id = R.string.acceptedRequests)
    val rejected_requests=stringResource(id = R.string.rejectedRequests)
    val accepted=stringResource(id = R.string.accepted)
    val rejected=stringResource(id = R.string.rejected)

    when(responseType){

        accepted_requests-> requestsList = myRequests(type =accepted)
        rejected_requests-> requestsList = myRequests(type = rejected)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        responseTopBar(navController,responseType)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            itemsIndexed(items = requestsList){ index,request->
                responseItem(
                    index,
                    requestsList,
                    navController,
                    responseType
                )
            }
        }
    }
}
@Composable
fun responseTopBar(navController: NavHostController,title:String) {
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
                            text = title,
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
@Composable
fun responseItem(index:Int,requests:List<RequestItems>,navController: NavHostController,requestType:String){

    var currRequest = remember { mutableStateOf(RequestItems())}
    val shoutDownDialog= remember { mutableStateOf(false)}
    if(shoutDownDialog.value)
        requestDisplay(shoutDownDialog = shoutDownDialog, request = currRequest, navController, requestType)
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                currRequest.value = requests[index]
                shoutDownDialog.value = true
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ){
            Icon(
                painter = painterResource(id = R.drawable.or_icon),
                contentDescription = "navigation icon",
                tint= colorResource(id = R.color.mainColor),
                modifier = Modifier.weight(1f).padding(10.dp)
            )
            Column (
                modifier = Modifier
                    .weight(5f)
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text=requests[index].organizationName,
                    modifier = Modifier.padding(2.dp)
                )
                Text(
                    text=requests[index].date_timeOfResponse,
                    modifier = Modifier.padding(4.dp),
                    color=Color.Gray
                )
            }
        }
    }
}