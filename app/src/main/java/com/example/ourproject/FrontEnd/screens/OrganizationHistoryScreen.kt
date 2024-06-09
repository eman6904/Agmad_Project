package com.example.ourproject.FrontEnd.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ourproject.BackEnd.Files.*
import com.example.ourproject.R
import java.util.Collections.addAll

@Composable
fun organizationHistory(navController: NavHostController){

    val acceptedRequestedNumber= rememberSaveable{ mutableStateOf(0) }
    val rejectedRequestedNumber= rememberSaveable{ mutableStateOf(0) }

    val accList= getAcceptedRequested()
    val rejList= getRejectedRequested()

    acceptedRequestedNumber.value=accList.size
    rejectedRequestedNumber.value=rejList.size

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HistoryTopBar(navController,"Organizations")
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ){

            Row(
                horizontalArrangement = Arrangement.Center
            ){
               if(getOrganizationData().profileImage=="") {
                   Image(
                       painterResource(R.drawable.user_icon),
                       modifier = Modifier
                           .clip(shape = CircleShape)
                           .weight(1f)
                           .padding(10.dp),
                       contentDescription = "",
                       colorFilter = ColorFilter.tint(colorResource(id = R.color.mainColor))
                   )
               }else{
                   AsyncImage(
                       model= getOrganizationData().profileImage,
                       modifier = Modifier
                           .clip(shape = CircleShape)
                           .weight(1f)
                           .padding(10.dp),
                       contentDescription = "",
                       colorFilter = ColorFilter.tint(colorResource(id = R.color.mainColor))
                   )
               }
                Column(
                    modifier= Modifier
                        .weight(3f)
                        .padding(10.dp)
                ){

                    Text(text= getOrganizationData().name, fontSize = 20.sp, fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier = Modifier.padding(3.dp)
                    )
                    Row(
                        modifier = Modifier.padding(3.dp)
                    ){
                        Icon(painterResource(id = R.drawable.id_icon),null, modifier = Modifier.weight(1f))
                        Text(
                            text= getOrganizationData().taxNumber,
                            modifier = Modifier
                                .weight(12f)
                                .padding(start = 5.dp))
                    }
                    Row(
                        modifier = Modifier.padding(3.dp)
                    ){
                        Icon(imageVector = Icons.Default.MyLocation,null)
                        Text(text= getOrganizationData().location,modifier= Modifier.padding(start=5.dp))
                    }
                }
            }

            Text(text= stringResource(R.string.my_impact),fontStyle = FontStyle.Normal,
                fontFamily = FontFamily(Font(R.font.bold)),
                fontSize = 20.sp,
                modifier= Modifier.padding(top=10.dp,start=10.dp),
            )
            Row (
                modifier= Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .height(IntrinsicSize.Min)
            ){

                Column(
                    modifier= Modifier.weight(1f)
                ){

                    Text(
                        text= stringResource(R.string.total_donations),
                        color = Color.Black,
                        modifier= Modifier.padding(10.dp),
                    )
                    Text(
                        text="${acceptedRequestedNumber.value+rejectedRequestedNumber.value}",fontStyle = FontStyle.Normal,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        fontSize = 30.sp,
                        color= Color.Gray,
                        modifier= Modifier.padding(start=10.dp),
                    )
                }
                Divider(
                    modifier = Modifier
                        .width(2.dp)
                        .padding(top = 20.dp)
                        .weight(0.02f)
                        .fillMaxHeight(),
                    color= Color.Gray
                )
                Column(
                    modifier= Modifier
                        .weight(2f)
                        .padding(top = 20.dp, end = 25.dp)
                ){
                    Text(
                        text = " ${rejectedRequestedNumber.value}",
                        color= Color.Red,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier= Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color= Color.White,
                        thickness = 5.dp
                    )
                    Text(
                        text = " ${acceptedRequestedNumber.value}",
                        color= Color.Green,
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier= Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier= Modifier.padding(top=10.dp)
                    ){

                        Icon(imageVector = Icons.Default.Circle, contentDescription = null,tint= Color.Red,
                            modifier= Modifier.weight(1f))
                        Text(
                            text= stringResource(R.string.rejected_requests),
                            modifier= Modifier
                                .padding(start = 5.dp)
                                .weight(20f)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ){

                        Icon(imageVector = Icons.Default.Circle, contentDescription = null,tint= Color.Green,
                            modifier= Modifier.weight(1f))
                        Text(
                            text= stringResource(R.string.accepted_requests),
                            modifier= Modifier
                                .padding(start = 5.dp)
                                .weight(20f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
//            Column(
//                modifier=Modifier.fillMaxSize()
//            ){
//                Text(text= stringResource(R.string.ratingbreakdown),
//                    fontFamily = FontFamily(Font(R.font.bold)),
//                    fontSize = 20.sp,
//                    modifier= Modifier.padding(top=10.dp,start=5.dp))
//
//                Spacer(modifier = Modifier.height(10.dp))
//
//
//             }

        }
    }
}
@Composable
fun ratingBreakdown(donorsNumber:Double, maxRating:Double, starsNumber:Int){

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){

        Text(text=starsNumber.toString())

        Icon(
            contentDescription = null,
            tint= Color.Black,
            imageVector = Icons.Rounded.Star,
            modifier = Modifier.size(20.dp)
        )


        var average: Double =donorsNumber/maxRating
        var progress by remember { mutableStateOf(average.toFloat())}
        val size by animateFloatAsState(
            targetValue = progress,
            tween(
                durationMillis = 1000,
                delayMillis = 200,
                easing = LinearOutSlowInEasing
            )
        )
        Column (
            modifier = Modifier
                .width(300.dp)
                .padding(start = 10.dp, end = 10.dp)
                .height(10.dp)
        ){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ){
                //background of progress bar
                Box(
                    modifier= Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.Gray)

                )
                //progress bar
                Box(
                    modifier= Modifier
                        .fillMaxWidth(size)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(9.dp))
                        .background(Color.Red)
                        .animateContentSize()
                )
            }

        }
        Text(text=donorsNumber.toString())
    }
}
