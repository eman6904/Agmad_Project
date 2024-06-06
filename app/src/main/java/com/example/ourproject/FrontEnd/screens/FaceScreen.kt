package com.example.ourproject.FrontEnd.screens

import android.content.res.Configuration
import android.os.Build
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Files.userType
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.util.*

@Composable
fun face(navController:NavHostController) {


    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.BottomEnd
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            shape = RoundedCornerShape(60.dp, 0.dp, 0.dp, 0.dp),
            backgroundColor = colorResource(id = R.color.mainColor),
            elevation = 5.dp
        ) {}
    }
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.TopStart
    ) {
        Card(
            modifier = Modifier.size(70.dp),
            shape = RoundedCornerShape(0.dp, 0.dp, 60.dp, 0.dp),
            backgroundColor = colorResource(id = R.color.mainColor),
            elevation = 5.dp
        ) {}
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {

            val scale = remember {
                androidx.compose.animation.core.Animatable(0f)
            }

            // Animation
            LaunchedEffect(key1 = true) {
                scale.animateTo(
                    targetValue = 0.7f,
                    // tween Animation
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = {
                            OvershootInterpolator(0.8f).getInterpolation(it)
                        })
                )
                 //Customize the delay time
                delay(2000L)
//                val user = Firebase.auth.currentUser
//
//                if(user==null)
//                   navController.navigate(ScreensRoute.RegisterAs.route)
//                else
//                    userType(navController)
                navController.navigate(ScreensRoute.SignIn.route)
            }
            Box(
                modifier = Modifier
                    .weight(1f).fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text= stringResource(R.string.food_ssver),
                    color= colorResource(id = R.color.mainColor),
                    fontFamily = FontFamily(Font(R.font.font4)),
                    fontSize = 45.sp,
                    modifier = Modifier.scale(scale.value).padding(top=20.dp),
                    textDecoration = TextDecoration.Underline,
                )
            }
            Box(
                modifier = Modifier.weight(1.5f).fillMaxSize()
            ){
                Image(
                    painterResource(R.drawable.logo),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "",
                )
            }
           Box(
               modifier = Modifier.weight(1f).fillMaxSize(),
               contentAlignment = Alignment.TopCenter
           ){
               Column(
                   modifier = Modifier.fillMaxSize().padding(top=20.dp),
                   horizontalAlignment = Alignment.CenterHorizontally,

               ){
                   Text(text= stringResource(R.string.intro1),
                       color= colorResource(id = R.color.mainColor),
                       fontSize = 15.sp,
                       fontFamily = FontFamily(Font(R.font.font2)),
                   )
                   Text(text= stringResource(R.string.intro2),
                       color= colorResource(id = R.color.mainColor),
                       fontSize = 15.sp,
                       fontFamily = FontFamily(Font(R.font.font2)),
                   )
               }
           }
        }
    }
}
