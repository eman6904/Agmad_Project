package com.example.ourproject.FrontEnd.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getRequests
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.MainActivity
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun organizationHome(navController: NavHostController) {

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
    var requestsNumber = rememberSaveable { mutableStateOf(0) }
    requestsList = getRequests()

    Column(
        modifier=Modifier.fillMaxSize()
    ) {

        requestsNumber.value=requestsList.size
        orHomeTopBar(requestsNumber,navController)
        Column() {
            val context= LocalContext.current
            val requests=stringResource(id = R.string.requests)
            var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }
            requestsList = getRequests()
            if(requestsList.isEmpty()){

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = stringResource(R.string.no_items),color=Color.Gray, fontSize = 15.sp)
                }
            }else{
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    itemsIndexed(items = requestsList){ index,request->
                        requestItem(
                            index,
                            requestsList,
                            navController,
                            requests,
                            context
                        )
                    }
                }
            }
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
    var language = rememberSaveable { mutableStateOf("") }
    var selectLanguage = rememberSaveable { mutableStateOf(false) }
    var showMenu = rememberSaveable { mutableStateOf(false) }

    if(selectLanguage.value==true){

        languageDialog(selectLanguage,language)
        if(language.value.isNotEmpty())
            MainActivity.sharedPreferences.edit().putString(MainActivity.SELECTED_LANGUAGE, language.value).apply()
        //and look at main activity
    }
    // Load the saved language and apply it

    if(language.value.isNotEmpty()){

        setLocale1(lang = language.value)
    }

    menuItems1(navController,showMenu,selectLanguage)

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
                        //لوحبينا نرجع الاشعارات هنشيل الكومنت دا بس
//                        IconButton(onClick = {
//                            requestNumber.value = 0
//                            navController.navigate(ScreensRoute.RequestsScreen.route+"/${requests}")
//                        }) {
//                            BadgedBox(badge = {
//                                if (showNotification.value) {
//                                    Badge {
//                                        Text(
//                                            text = requestNumber.value.toString(),
//                                            color = Color.White
//                                        )
//                                    }
//                                }
//                            }) {
//                                Icon(
//                                    imageVector = Icons.Default.Notifications,
//                                    contentDescription = "notification icon",
//                                    tint = Color.White
//                                )
//                            }
//                        }
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
fun menuItems1(navController:NavHostController, showMenu:MutableState<Boolean>,selectLan:MutableState<Boolean>){


    val accepted_requests=stringResource(id = R.string.acceptedRequests)
    val rejected_requests=stringResource(id = R.string.rejectedRequests)
    val context = LocalContext.current

    if(showMenu.value){
        Box(){
            DropdownMenu(
                expanded = showMenu.value,
                onDismissRequest = { showMenu.value=false },
                offset = DpOffset(x = (160).dp, y = (5).dp)
            )
            {
                DropdownMenuItem(
                    onClick = {
                        navController.navigate(ScreensRoute.RequestsScreen.route+"/${accepted_requests}")
                        showMenu.value=false
                    }
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
                    onClick = {
                        navController.navigate(ScreensRoute.RequestsScreen.route+"/${rejected_requests}")
                        showMenu.value=false
                    }
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
                DropdownMenuItem(
                    onClick = {
                        selectLan.value=true
                        showMenu.value=false
                    }
                ) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Text(
                            text = stringResource(R.string.language),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                DropdownMenuItem(
                    onClick = {
                        FirebaseAuth.getInstance().signOut()
                        (context as? Activity)?.finishAffinity()
                        showMenu.value=false
                    }
                ) {
                    Row() {
                        Icon(
                            imageVector = Icons.Default.Logout,
                            contentDescription = null,
                            tint = Color.Gray
                        )
                        Text(
                            text = stringResource(R.string.logout),
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

            }
        }
    }
}