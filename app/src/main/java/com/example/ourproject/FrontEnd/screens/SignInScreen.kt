package com.example.ourproject.FrontEnd.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Files.userSignIn
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth


@Composable
fun signIn(navController: NavHostController) {

    val email = rememberSaveable() { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val showProgress = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogD = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogE = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogV = rememberSaveable() { mutableStateOf(false) }
    val shoutDownDialogR = rememberSaveable() { mutableStateOf(false) }
    var emptyPassword = rememberSaveable() { mutableStateOf(false)}
    var emptyEmail = rememberSaveable() { mutableStateOf(false)}

    val modifierForEmptyField = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp)
        .height(55.dp)
        .border(2.dp, Color.Red, RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
    val modifierForNotEmptyField = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp)
        .height(55.dp)

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
            Image(
                painterResource(R.drawable.sign_ph),
                contentDescription = "",
                // contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            if(emptyEmail.value==false||email.value.isNotEmpty())
                emailField(email,modifierForNotEmptyField)
            else
                emailField(email,modifierForEmptyField)

            if(emptyPassword.value==false||password.value.isNotEmpty())
                passwordField(password,modifierForNotEmptyField)
            else
                passwordField(password,modifierForEmptyField)

            ErrorDialog(shoutDownDialog = shoutDownDialogE)
            DataDialog(shoutDownDialog = shoutDownDialogD)
            DialogForResetPassword(shoutDownDialogR)
            VerificationDialog(shoutDownDialog = shoutDownDialogV)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                buttonSignIn(
                    stringResource(R.string.signi_in),
                    email,
                    password,
                    showProgress,
                    shoutDownDialogD,
                    shoutDownDialogE,
                    shoutDownDialogV,
                    navController,
                    emptyEmail,
                    emptyPassword
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    checkBox(item = stringResource(R.string.remember_me))
                }
                progressBar(showProgress)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    color = colorResource(id = R.color.mainColor)
                )
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    ClickableText(text = AnnotatedString(stringResource(R.string.forget_your_password)),
                        onClick =
                        {
                            shoutDownDialogR.value = true
                        }
                    )
                }
                Divider(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 4.dp),
                    color = colorResource(id = R.color.mainColor)
                )
            }
            Row(

            ) {
                Text(text = stringResource(R.string.dont_have_account))
                ClickableText(text = AnnotatedString(stringResource(R.string.signup)),
                    style = TextStyle(
                        color = colorResource(id = R.color.mainColor),
                    ), onClick = {

                            navController.navigate(ScreensRoute.RegisterAs.route)
                    })
            }
        }
    }
}

@Composable
fun buttonSignIn(
    buttonName: String,
    email: MutableState<String>,
    password: MutableState<String>,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    navController: NavHostController,
    emptyEmail:MutableState<Boolean>,
    emptyPassword:MutableState<Boolean>
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            onClick = {

                if(password.value.isEmpty()){

                    emptyPassword.value=true
                }
                else
                    emptyPassword.value=false

                if(email.value.isEmpty()){

                    emptyEmail.value=true
                }else{
                    emptyEmail.value=false
                }

                if(email.value.isNotEmpty()&&password.value.isNotEmpty()){
                    shoutDownProgress.value = true
                    userSignIn(
                        email, password, shoutDownProgress,
                        showMsgD, showError, showMsgV, navController
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 20.dp, end = 20.dp),
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
fun DialogForResetPassword(shoutDownDialog: MutableState<Boolean>) {

    val email = rememberSaveable() { mutableStateOf("") }
    if (shoutDownDialog.value) {
        Dialog(
            onDismissRequest = { shoutDownDialog.value = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        modifier = Modifier.padding(20.dp),
                        label = { Text(text = stringResource(id = R.string.enter_email)) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = colorResource(id = R.color.mainColor),
                            focusedLabelColor = colorResource(id = R.color.mainColor),
                            unfocusedIndicatorColor = colorResource(id = R.color.mainColor),
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = colorResource(id = R.color.mainColor)
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                    )
                    Button(
                        onClick = {
                            shoutDownDialog.value = false
                            FirebaseAuth.getInstance().sendPasswordResetEmail(email.value)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Log.d("msg", "Email sent.")
                                    } else {
                                        Log.d("msg", "an error ocurred")
                                    }
                                }

                        },
                        content = {
                            Text(
                                text = stringResource(R.string.send),
                                color = Color.White
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.mainColor)
                        ),
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
        }
    }
}