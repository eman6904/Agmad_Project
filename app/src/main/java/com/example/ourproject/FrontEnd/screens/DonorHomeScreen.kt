package com.example.ourproject.FrontEnd.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Files.myRequests
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.MainActivity
import com.example.ourproject.MainActivity.Companion.SELECTED_LANGUAGE
import com.example.ourproject.MainActivity.Companion.sharedPreferences
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*


@Composable
fun donorHome(navController: NavHostController){


    val context=LocalContext.current

    Column {

        val acceptedRequestedNumber= rememberSaveable{ mutableStateOf(0)}
        val rejectedRequestedNumber= rememberSaveable{ mutableStateOf(0)}

        val accList= myRequests(typeInArabic = "مقبول", typeInEnglish = "Accepted")
        val rejList= myRequests(typeInArabic = "مرفوض", typeInEnglish = "Rejected")

        acceptedRequestedNumber.value=accList.size
        rejectedRequestedNumber.value=rejList.size

        doHomeTopBar(acceptedRequestedNumber,rejectedRequestedNumber,navController,context)
        Column() {

        }
    }

    BackHandler() {
        // Exit the app when the back button is pressed
        (context as? Activity)?.finish()
    }
}
@Composable
fun doHomeTopBar(acceptedRequestedNumber: MutableState<Int>,rejectedRequestedNumber: MutableState<Int>,
                 navController: NavHostController,_context: Context) {

    val accepted_requests=stringResource(id = R.string.acceptedRequests)
    val rejected_requests=stringResource(id = R.string.rejectedRequests)
    var language = rememberSaveable { mutableStateOf("") }
    var showNotificationForAcc = rememberSaveable { mutableStateOf(false) }
    var showNotificationForRej = rememberSaveable { mutableStateOf(false) }
    var showMenu = rememberSaveable { mutableStateOf(false) }
    var selectLanguage = rememberSaveable { mutableStateOf(false) }

    if(selectLanguage.value==true){

        languageDialog(selectLanguage,language)
        if(language.value.isNotEmpty())
            sharedPreferences.edit().putString(SELECTED_LANGUAGE, language.value).apply()
        //and look at main activity
    }
    // Load the saved language and apply it

    if(language.value.isNotEmpty()){

        setLocale1(lang = language.value)
    }

    menuItems2(showMenu,selectLanguage,navController)

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
                    title = { Text(text = stringResource(R.string.home),
                        color = Color.White,
                        modifier = Modifier.padding(start=10.dp))
                    },
                    actions = {
                        IconButton(onClick = {
                            rejectedRequestedNumber.value = 0
                            navController.navigate(ScreensRoute.Response.route+"/$rejected_requests")
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
                            navController.navigate(ScreensRoute.Response.route+"/$accepted_requests")
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
                        IconButton(onClick = {

                        }) {
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
fun setLocale1(lang: String?) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val systemLocale = configuration.locale

    // Create a Locale object based on the provided language code
    // val newLocale = if (lang != null) Locale(lang) else systemLocale
    val locale = if (lang != null) Locale(lang) else systemLocale
    Locale.setDefault(locale)
    val config = Configuration(context.resources.configuration)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        config.setLocale(locale)
    } else {
        config.locale = locale
    }
    context.resources.updateConfiguration(config, context.resources.displayMetrics)

    val refreshIntent = Intent(context,MainActivity::class.java)
    refreshIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(refreshIntent)
}
@Composable
fun languageDialog(shoutDownDialog: MutableState<Boolean>,selectedLan:MutableState<String>) {

    val scrollState = rememberScrollState()
    if (shoutDownDialog.value) {

        Dialog(
            onDismissRequest = { shoutDownDialog.value = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .verticalScroll(state = scrollState),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp).fillMaxSize()
                ) {

                    radioButton(selectedLan)
                }
            }

        }
    }
}
@Composable
fun menuItems2(showMenu:MutableState<Boolean>,selectLan:MutableState<Boolean>,navController: NavHostController) {

    val context = LocalContext.current

    if (showMenu.value) {
        DropdownMenu(
            expanded = showMenu.value,
            onDismissRequest = { showMenu.value = false },
            offset = DpOffset(x = (160).dp, y = (5).dp)
        )
        {
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