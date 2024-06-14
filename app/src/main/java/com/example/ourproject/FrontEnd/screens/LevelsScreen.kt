package com.example.ourproject.FrontEnd.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Classes.ListsGroup
import com.example.ourproject.BackEnd.DataClasses.LevelItems
import com.example.ourproject.BackEnd.Files.myRequests
import com.example.ourproject.BackEnd.Files.selectLevel
import com.example.ourproject.MainActivity
import com.example.ourproject.R

@Composable
fun levels(navController: NavHostController) {

    val scrollState = rememberScrollState()

    val acceptedRequestedNumber = rememberSaveable { mutableStateOf(0) }
    val rejectedRequestedNumber = rememberSaveable { mutableStateOf(0) }

    val accList = myRequests(typeInArabic = "مقبول", typeInEnglish = "Accepted")
    val rejList = myRequests(typeInArabic = "مرفوض", typeInEnglish = "Rejected")

    acceptedRequestedNumber.value = accList.size
    rejectedRequestedNumber.value = rejList.size

    val selectedLevel = selectLevel(acceptedRequestedNumber.value)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LevelsTopBar(navController)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .weight(2f)
        ) {

            Icon(
                imageVector = Icons.Default.Circle, contentDescription = null, tint = colorResource(
                    id = selectedLevel.color
                ),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize()
                    .weight(2f)
            )
            Text(
                text = selectedLevel.name,
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.bold)),
                modifier = Modifier.weight(1f)
            )
            customProgressBar(selectedLevel, acceptedRequestedNumber.value.toDouble())
        }

        Column(
            modifier = Modifier
                .weight(3f)
                .verticalScroll(scrollState)
        ) {

            val listsGroup = ListsGroup()
            listsGroup.setLevels()
            for (level in listsGroup.levels) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(start = 20.dp, bottom = 10.dp, end = 20.dp)
                        .weight(1f)

                ) {
                    Icon(
                        imageVector = Icons.Default.Circle, contentDescription = null,
                        tint = colorResource(id = level.color),
                        modifier = Modifier
                    )
                    Text(
                        text = level.name,
                        fontSize = 10.sp,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier = Modifier.padding(5.dp)
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(text = level.startPoint.toString())
                    }
                }
                Divider(
                    thickness = 3.dp, modifier = Modifier
                        .clip(CircleShape)
                        .padding(start = 20.dp, end = 20.dp), color = Color.LightGray
                )
            }
        }
    }
}

@Composable
fun LevelsTopBar(navController: NavHostController) {

    var showMenu = rememberSaveable { mutableStateOf(false) }
    var selectLanguage = rememberSaveable { mutableStateOf(false) }
    var language = rememberSaveable { mutableStateOf("") }

    if (selectLanguage.value == true) {

        languageDialog(selectLanguage, language)
        if (language.value.isNotEmpty())
            MainActivity.sharedPreferences.edit()
                .putString(MainActivity.SELECTED_LANGUAGE, language.value).apply()
        //and look at main activity
    }
    // Load the saved language and apply it

    if (language.value.isNotEmpty()) {

        setLocale1(lang = language.value)
    }

    menuItems2(showMenu, selectLanguage, navController)
    Card(
        modifier = Modifier
            .background(color = Color.White)
            .height(90.dp)
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
                            text = stringResource(R.string.levels),
                            color = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
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
                    actions = {
                        IconButton(
                            onClick = {
                                showMenu.value = !showMenu.value
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
fun customProgressBar(selectedLevel: LevelItems, donationsNumber: Double) {

    var currentLevel = selectedLevel.endPoint
    var average: Double = (donationsNumber / currentLevel) + 0.1
    var progress by remember { mutableStateOf(average.toFloat()) }
    val size by animateFloatAsState(
        targetValue = progress,
        tween(
            durationMillis = 1000,
            delayMillis = 200,
            easing = LinearOutSlowInEasing
        )
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .widthIn(min = 30.dp)
                .fillMaxWidth(size),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "${donationsNumber.toInt()}")
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
        ) {
            //background of progress bar
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(9.dp))
                    .background(Color.Gray)

            )
            //progress bar
            Box(
                modifier = Modifier
                    .fillMaxWidth(size)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(9.dp))
                    .background(colorResource(id = selectedLevel.color))
                    .animateContentSize()
            )
        }

    }

}
