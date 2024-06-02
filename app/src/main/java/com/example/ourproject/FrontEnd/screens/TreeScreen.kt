package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.ourproject.BackEnd.Files.myRequests
import com.example.ourproject.R

@Composable
fun TreeAnimation() {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.tree_with_flowers))
    val acceptedRequestedNumber= rememberSaveable{ mutableStateOf(0) }
    val accList= myRequests(typeInArabic = "مقبول", typeInEnglish = "Accepted")
    acceptedRequestedNumber.value=accList.size
    val treeProgress = rememberSaveable(acceptedRequestedNumber.value) {
        when(acceptedRequestedNumber.value) {
            0 -> 0f
            in 1 .. 2 -> 0.05f
            in 3 .. 4 -> 0.1f
            in 5 .. 6 -> 0.15f
            in 7 .. 8 -> 0.2f
            in 9 .. 10 -> 0.25f
            in 11 .. 12 -> 0.3f
            in 13 .. 15 -> 0.35f
            in 16 .. 18 -> 0.45f
            in 19 .. 20 -> 0.55f
            in 21 .. 23 -> 0.6f
            in 24 .. 26 -> 0.65f
            in 27 .. 29 -> 0.7f
            else -> 1f
        }
    }

    Spacer(modifier = Modifier.height(42.dp))
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        PlantTopBar()
        LottieAnimation(
            modifier = Modifier.size(400.dp),
            composition = composition,
            progress = treeProgress
        )
    }
}

@Composable
fun PlantTopBar() {
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
                    title = { Text(text = stringResource(R.string.my_plant),
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

@Preview
@Composable
fun TreeAnimationPreview() {
    TreeAnimation()
}