package com.example.ourproject.FrontEnd.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R
import kotlinx.coroutines.delay
@Composable
fun registerAs(navController:NavHostController){

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
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
        ) {
            Text(text= stringResource(R.string.registeras),color= colorResource(id = R.color.mainColor),
                fontFamily = FontFamily(Font(R.font.bold)),
                fontSize = 25.sp,
                textDecoration = TextDecoration.Underline
            )
            Image(
                painterResource(R.drawable.logo),
                contentDescription = "",
                // contentScale = ContentScale.Crop,
                modifier = Modifier
                    .weight(2f)
                    .fillMaxWidth()
            )
            Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .weight(1f)
                    .padding(start=20.dp,end=20.dp)
                    .weight(1f)
            ){
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end=15.dp,top=5.dp,bottom=65.dp,start=35.dp),
                    elevation=10.dp,
                    shape = RoundedCornerShape(16.dp),
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(10f).padding(2.dp)
                    ) {
                        Image(
                            painterResource(R.drawable.or_ph2),
                            //  modifier = Modifier.size(100.dp),
                            contentDescription = "",
                            modifier = Modifier.weight(7f).padding(5.dp)
                        )
                        ClickableText(text = AnnotatedString(stringResource(R.string.organization)) ,
                            onClick ={navController.navigate(ScreensRoute.OrganizationSignUp.route)},
                            modifier = Modifier.weight(3f),
                            style = TextStyle(
                                 fontFamily = FontFamily(Font(R.font.bold2))
                            )
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end=35.dp,top=5.dp,bottom=65.dp,start=15.dp),
                    elevation=10.dp,
                    shape = RoundedCornerShape(16.dp),
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(10f).padding(2.dp)
                    ) {
                        Image(
                            painterResource(R.drawable.do_ph2),
                            modifier = Modifier
                                .size(150.dp)
                                .weight(7f)
                                .padding(5.dp),
                            contentDescription = "",
                        )
                        ClickableText(text = AnnotatedString(stringResource(R.string.donor)),
                            onClick ={navController.navigate(ScreensRoute.SignUP.route)},
                            modifier = Modifier.weight(3f),
                            style = TextStyle(
                                 fontFamily = FontFamily(Font(R.font.bold2))
                            )
                        )
                    }
                }
            }
        }

    }
}
