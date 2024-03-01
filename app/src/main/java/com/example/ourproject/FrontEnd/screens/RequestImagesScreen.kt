package com.example.ourproject.FrontEnd.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getAcceptedRequested
import com.example.ourproject.BackEnd.Files.getRejectedRequested
import com.example.ourproject.BackEnd.Files.getRequests

@Composable
fun requestImages(navController: NavHostController,requestId:String,requestType:String){

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
    when(requestType){

        "Requests"-> requestsList = getRequests()
        "Accepted Requests"-> requestsList = getAcceptedRequested()
        "Rejected Requests"-> requestsList = getRejectedRequested()
    }

    var imageUris by remember { mutableStateOf(emptyList<String>()) }
    for(item in requestsList)
    {
        if(item.requestId==requestId){
            imageUris=item.imagesList
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        foodCintentTopBar(navController)
        LazyColumn {

            items(items=imageUris, itemContent = {item->
                AsyncImage(
                    modifier = Modifier.padding(20.dp),
                    model = item,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(10.dp))
            })
        }

    }

}