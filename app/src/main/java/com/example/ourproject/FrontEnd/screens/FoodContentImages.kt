package com.example.ourproject.FrontEnd.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun foodContent(navController: NavHostController,imagesIdList:List<String>) {



    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid

    var imageUris by remember { mutableStateOf(emptyList<String>()) }

    // Function to fetch image URIs from Firebase Storage
    LaunchedEffect(true) {
        val storageRef = FirebaseStorage.getInstance().reference.child(currentUserId+"/")
        val images = mutableListOf<String>()
        storageRef.listAll().await().items.forEach { imageRef ->

            if(imagesIdList.contains(imageRef.name)) {
                val uri = imageRef.downloadUrl.await().toString()
                images.add(uri)
            }
        }
        imageUris = images
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        foodContentTopBar(navController)

        if(imageUris.size==0) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.waiting),color=Color.Gray)
            }
        }else{

            LazyColumn(
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

@Composable
fun foodContentTopBar(navController: NavHostController) {
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
                            text = stringResource(R.string.foodContent),
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