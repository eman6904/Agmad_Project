package com.example.ourproject.FrontEnd.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.LevelItems
import com.example.ourproject.BackEnd.Files.getDonorData
import com.example.ourproject.BackEnd.Files.myRequests
import com.example.ourproject.BackEnd.Files.selectLevel
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.MainActivity
import com.example.ourproject.R


@Composable
fun historyScreen(navController: NavHostController){


    val acceptedRequestedNumber= rememberSaveable{ mutableStateOf(0)}
    val rejectedRequestedNumber= rememberSaveable{ mutableStateOf(0)}

    val accList= myRequests(typeInArabic = "مقبول", typeInEnglish = "Accepted")
    val rejList= myRequests(typeInArabic = "مرفوض", typeInEnglish = "Rejected")

    acceptedRequestedNumber.value=accList.size
    rejectedRequestedNumber.value=rejList.size

    val selectedLevel= selectLevel(acceptedRequestedNumber.value)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HistoryTopBar()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ){

            Row(
                horizontalArrangement = Arrangement.Center
            ){

                Image(
                    painterResource(R.drawable.user_icon),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .weight(1f)
                        .padding(10.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.mainColor))
                )
                Column(
                    modifier= Modifier
                        .weight(3f)
                        .padding(10.dp)
                ){

                    Text(text= getDonorData().name, fontSize = 20.sp, fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier = Modifier.padding(3.dp)
                    )
                    Row(
                        modifier = Modifier.padding(3.dp)
                    ){
                        Icon(painterResource(id = R.drawable.id_icon),null, modifier = Modifier.weight(1f))
                        Text(
                            text= getDonorData().phone,
                            modifier = Modifier
                                .weight(12f)
                                .padding(start = 5.dp))
                    }
                    Row(
                        modifier = Modifier.padding(3.dp)
                    ){
                        Icon(imageVector = Icons.Default.MyLocation,null)
                        Text(text= getDonorData().location,modifier=Modifier.padding(start=5.dp))
                    }
                    ratingBar(2.5)
                }
            }

            Text(text="My Impact:",fontStyle = FontStyle.Normal,
                fontFamily = FontFamily(Font(R.font.bold)),
                fontSize = 20.sp,
                modifier=Modifier.padding(top=10.dp,start=10.dp),
            )
            Row (
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .height(IntrinsicSize.Min)
            ){

                Column(
                    modifier=Modifier.weight(1f)
                ){

                    Text(
                        text="Total donations:",
                        color = Color.Black,
                        modifier=Modifier.padding(10.dp),
                    )
                    Text(
                        text="${acceptedRequestedNumber.value+rejectedRequestedNumber.value}",fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        fontSize = 30.sp,
                        color=Color.Gray,
                        modifier=Modifier.padding(start=10.dp),
                    )
                }
                Divider(
                    modifier = Modifier
                        .width(2.dp)
                        .padding(top = 20.dp)
                        .weight(0.02f)
                        .fillMaxHeight(),
                    color=Color.Gray
                )
                Column(
                    modifier= Modifier
                        .weight(2f)
                        .padding(top = 20.dp, end = 25.dp)
                ){
                   Text(
                       text = " ${rejectedRequestedNumber.value}",
                       color=Color.Red,
                       fontFamily = FontFamily(Font(R.font.bold)),
                       modifier= Modifier
                           .fillMaxWidth()
                           .background(Color.LightGray)
                   )
                   Divider(
                       modifier =Modifier
                           .fillMaxWidth(),
                       color=Color.White,
                       thickness = 5.dp
                   )
                    Text(
                        text = " ${acceptedRequestedNumber.value}",
                        color=Color.Green,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier= Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                   Row(
                       horizontalArrangement = Arrangement.Center,
                       verticalAlignment = Alignment.CenterVertically,
                       modifier=Modifier.padding(top=10.dp)
                   ){

                       Icon(imageVector = Icons.Default.Circle, contentDescription = null,tint=Color.Red,
                           modifier=Modifier.weight(1f))
                       Text(
                           text="Rejected requests",
                           modifier= Modifier
                               .padding(start = 5.dp)
                               .weight(20f)
                       )
                   }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Icon(imageVector = Icons.Default.Circle, contentDescription = null,tint=Color.Green,
                            modifier=Modifier.weight(1f))
                        Text(
                            text="Accepted requests",
                            modifier= Modifier
                                .padding(start = 5.dp)
                                .weight(20f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(72.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(ScreensRoute.Levels.route) },
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint= colorResource(id = selectedLevel.color)
                    )
                    Text(
                        text= "My Level",
                        modifier= Modifier.padding(start = 12.dp),
                        color = Color.Black
                    )
                }

                OutlinedButton(
                    onClick = { navController.navigate(ScreensRoute.Plant.route) },
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Icon(
                        painterResource(id = R.drawable.plant),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = colorResource(id = R.color.mainColor)
                    )
                    Text(
                        text="My Plant",
                        modifier= Modifier.padding(start = 12.dp),
                        color = Color.Black
                    )
                }
            }
        }
    }
}
@Composable
fun HistoryTopBar() {

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
                    title = { Text(text = stringResource(R.string.history),
                        color = Color.White,
                        modifier = Modifier.padding(start=10.dp))
                    },
                    actions = {
                        IconButton(
                            onClick = {
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
fun ratingBar(rating:Double=0.0, ){

    Row(){

        var isHalfStar=(rating%1)!=0.0
        for(index in 1..5){

            Icon(
                contentDescription = null,
                tint= colorResource(id = R.color.yellow),
                imageVector = if(index<=rating){
                    Icons.Rounded.Star
                }else{

                    if(isHalfStar) {

                        isHalfStar=false
                        Icons.Rounded.StarHalf
                    }else{
                        Icons.Rounded.StarOutline
                    }
                }
            )
        }
    }
}

