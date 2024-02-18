package com.example.ourproject.FrontEnd.screens

import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

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
                val user = Firebase.auth.currentUser
               // Log.d("lllllllllllllll",user.toString())
//                if(user==null)
//                   navController.navigate(ScreensRoute.RegisterAs.route)
//                else
//                    navController.navigate(BottomBarScreen.Home.route)
                navController.navigate(ScreensRoute.SignIn.route)
            }
            Text(text= stringResource(R.string.food_ssver),
                color= colorResource(id = R.color.mainColor),
                fontFamily = FontFamily(Font(R.font.font4)),
                fontSize = 45.sp,
                modifier = Modifier.scale(scale.value),
                textDecoration = TextDecoration.Underline,
            )
            Image(
                painterResource(R.drawable.logo),
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "",
            )
           Column(
               modifier = Modifier.fillMaxWidth(),
               horizontalAlignment = Alignment.CenterHorizontally,
               verticalArrangement = Arrangement.Center
           ){
               Text(text= stringResource(R.string.intro1),
                   color= colorResource(id = R.color.mainColor),
                   fontFamily = FontFamily(Font(R.font.font2)),
                   modifier = Modifier.scale(scale.value)
               )
               Text(text= stringResource(R.string.intro2),
                   color= colorResource(id = R.color.mainColor),
                   fontFamily = FontFamily(Font(R.font.font2)),
                   modifier = Modifier.scale(scale.value),
               )
           }
        }
    }
}