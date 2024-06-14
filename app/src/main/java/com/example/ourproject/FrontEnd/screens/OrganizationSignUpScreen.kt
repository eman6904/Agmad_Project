package com.example.ourproject.FrontEnd.screens

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
fun organizationSignUp(navController: NavHostController) {

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
    var emptyPassword = rememberSaveable() { mutableStateOf(false) }
    var emptyEmail = rememberSaveable() { mutableStateOf(false) }
    var emptyConPassword = rememberSaveable() { mutableStateOf(false) }
    var emptyName = rememberSaveable() { mutableStateOf(false) }
    var emptyLocation = rememberSaveable() { mutableStateOf(false) }
    var emptyTaxNumber = rememberSaveable() { mutableStateOf(false) }
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
        IconButton(onClick = {
            navController.popBackStack()
        }) {
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
        Text(
            text = stringResource(R.string.or_sign_up),
            color = colorResource(id = R.color.mainColor),
            fontFamily = FontFamily(Font(R.font.bold)),
            fontSize = 25.sp,
            textDecoration = TextDecoration.Underline
        )
        if (emptyName.value == false || name.value.isNotEmpty())
            nameField(name, modifierForNotEmptyField)
        else
            nameField(name, modifierForEmptyField)

        if (emptyEmail.value == false || email.value.isNotEmpty())
            emailField(email, modifierForNotEmptyField)
        else
            emailField(email, modifierForEmptyField)

        if (emptyPassword.value == false || password.value.isNotEmpty())
            passwordField(password, modifierForNotEmptyField)
        else
            passwordField(password, modifierForEmptyField)

        if (emptyConPassword.value == false)
            confirmPasswordField(conPassword, modifierForNotEmptyField)
        else
            confirmPasswordField(conPassword, modifierForEmptyField)

        if (emptyTaxNumber.value == false || taxNumber.value.isNotEmpty())
            taxNumber(taxNumber, modifierForNotEmptyField)
        else
            taxNumber(taxNumber, modifierForEmptyField)

        if (emptyLocation.value == false || location.value.isNotEmpty())
            location(location, modifierForNotEmptyField)
        else
            location(location, modifierForEmptyField)
        buttonSignUpOr(
            stringResource(R.string.sign_up),
            name,
            email,
            password,
            conPassword,
            taxNumber,
            location,
            showProgress,
            shoutDownDialogD,
            shoutDownDialogE,
            shoutDownDialogV,
            navController,
            emptyPassword,
            emptyConPassword,
            emptyName,
            emptyTaxNumber,
            emptyEmail,
            emptyLocation
        )
        progressBar(showProgress)
        ErrorDialog(shoutDownDialog = shoutDownDialogE)
        signUpError(shoutDownDialog = shoutDownDialogD)
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
    conPassword: MutableState<String>,
    taxNumber: MutableState<String>,
    location: MutableState<String>,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    navController: NavHostController,
    emptyPassword: MutableState<Boolean>,
    emptyConPassword: MutableState<Boolean>,
    emptyName: MutableState<Boolean>,
    emptyTaxNumber: MutableState<Boolean>,
    emptyEmail: MutableState<Boolean>,
    emptyLocation: MutableState<Boolean>,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {

        Button(
            onClick = {
                if (password.value.isEmpty()) {
                    emptyPassword.value = true

                } else
                    emptyPassword.value = false

                if (conPassword.value.isEmpty() || conPassword.value != password.value) {
                    emptyConPassword.value = true;

                } else {
                    emptyConPassword.value = false
                }

                if (taxNumber.value.isEmpty()) {
                    emptyTaxNumber.value = true;

                } else {
                    emptyTaxNumber.value = false
                }

                if (email.value.isEmpty()) {
                    emptyEmail.value = true;

                } else {
                    emptyEmail.value = false
                }

                if (location.value.isEmpty()) {
                    emptyLocation.value = true;

                } else {
                    emptyLocation.value = false
                }

                if (name.value.isEmpty()) {
                    emptyName.value = true;

                } else {
                    emptyName.value = false
                }
                if (name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty()
                    && conPassword.value.isNotEmpty() && taxNumber.value.isNotEmpty() && location.value.isNotEmpty() && conPassword.value == password.value
                ) {
                    shoutDownProgress.value = true
                    organization_signUp(
                        name, email, password, taxNumber, location,
                        shoutDownProgress, showMsgD, showError, showMsgV, navController
                    )
                }
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
fun taxNumber(name: MutableState<String>, modifier: Modifier) {

    Card(
        modifier = modifier,
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
fun location(location: MutableState<String>, modifier: Modifier) {
    Card(
        modifier = modifier,
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
