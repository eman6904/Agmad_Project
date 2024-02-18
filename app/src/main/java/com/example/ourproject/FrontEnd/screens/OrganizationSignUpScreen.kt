package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Files.organization_signUp
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R
@Composable
fun organizationSignUp(navController:NavHostController){

    val name = rememberSaveable() { mutableStateOf("") }
    val email = rememberSaveable() { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val conPassword = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val taxNumber = rememberSaveable { mutableStateOf("") }
    val showProgress = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogD = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogE = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogV = rememberSaveable() { mutableStateOf(false) }
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
        IconButton(onClick = {}) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.arrowbackicon),
                tint = Color.White
            )
        }
    }
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
        ) {
            Text(text= stringResource(R.string.or_sign_up),color= colorResource(id = R.color.mainColor),
                fontFamily = FontFamily(Font(R.font.bold)),
                fontSize = 25.sp,
                textDecoration = TextDecoration.Underline
            )
            nameField(name)
            emailField(email)
            passwordField(password)
            confirmPasswordField(conPassword)
            taxNumber(taxNumber)
            location(location)
            buttonSignUpOr(
                stringResource(R.string.sign_up), name, email, password,taxNumber,location,
                showProgress, shoutDownDialogD, shoutDownDialogE, shoutDownDialogV, navController
            )
            progressBar(showProgress)
            ErrorDialog(shoutDownDialog = shoutDownDialogE)
            DataDialog(shoutDownDialog = shoutDownDialogD)
            VerificationDialog(shoutDownDialog = shoutDownDialogV)
            Row(

            ) {
                Text(text = stringResource(R.string.have_account))
                ClickableText(text = AnnotatedString(stringResource(R.string.signin)),
                    style = TextStyle(
                        color = colorResource(id = R.color.mainColor),
                    ), onClick = { navController.navigate(ScreensRoute.SignIn.route) })
            }
        }
}
@Composable
fun buttonSignUpOr(
    buttonName: String,
    name: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    taxNumber: MutableState<String>,
    location: MutableState<String>,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    navController: NavHostController
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = {
                shoutDownProgress.value = true
                 organization_signUp(name,email,password,taxNumber,location,
                     shoutDownProgress,showMsgD,showError,showMsgV,navController)
            },
            modifier = Modifier
                .width(330.dp)
                .height(50.dp),
            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = colorResource(id = R.color.mainColor),
            )
        ) {
            Text(text = buttonName, fontSize = 20.sp)
        }
    }
}
@Composable
fun taxNumber(name: MutableState<String>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=20.dp,end=20.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = { Text(text = stringResource(R.string.tax_number)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.mainColor),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
    }
}
@Composable
fun location(location: MutableState<String>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=20.dp,end=20.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            value = location.value,
            onValueChange = { location.value = it },
            placeholder = { Text(text = stringResource(R.string.location)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.mainColor),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
        )
    }

}
