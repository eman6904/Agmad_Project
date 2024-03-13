package com.example.ourproject.FrontEnd.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.*
import com.example.ourproject.R

@Composable
fun responseImages(navController: NavHostController,requestId:String,requestType:String){

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
    val accepted_requests= stringResource(id = R.string.acceptedRequests)
    val rejected_requests= stringResource(id = R.string.rejectedRequests)
    val accept= stringResource(id = R.string.accepted)
    val reject= stringResource(id = R.string.rejected)

    when(requestType){

        accepted_requests-> requestsList= myRequests(type = accept)
        rejected_requests->   requestsList= myRequests(type = reject)
    }

    var imageUris by remember { mutableStateOf(emptyList<String>()) }
    for(item in requestsList)
    {
        if(item.requestId==requestId){
            imageUris= getImages(imagesId = item.imagesList, item.donorId)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        foodCintentTopBar(navController)

        if(imageUris.size==0){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = "Waiting...",color= Color.Gray)
            }
        }else{

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ){

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

}