package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.Files.Donor_signUp
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.R
import com.example.ourproject.FrontEnd.ScreensRoute
import com.owlbuddy.www.countrycodechooser.CountryCodeChooser
import com.owlbuddy.www.countrycodechooser.utils.enums.CountryCodeType

@Composable
fun DonorSignUp(navController: NavHostController) {

    val name = rememberSaveable() { mutableStateOf("") }
    val email = rememberSaveable() { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val conPassword = rememberSaveable { mutableStateOf("") }
    val showProgress = rememberSaveable() { mutableStateOf(false) }
    val phone = rememberSaveable() { mutableStateOf("") }
    var selectedGender by rememberSaveable() { mutableStateOf("Gender") }
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
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize()
        ) {
            Text(text= stringResource(R.string.donor_sign_up),color= colorResource(id = R.color.mainColor),
                fontFamily = FontFamily(Font(R.font.bold)),
                fontSize = 25.sp,
                textDecoration = TextDecoration.Underline
            )
            nameField(name)
            emailField(email)
            passwordField(password)
            confirmPasswordField(conPassword)
            SelectCountryWithCountryCode(phone)
            val Gender =
                listOf<String>(stringResource(R.string.male), stringResource(R.string.female))
            GenderSpinner(
                itemList = Gender,
                selectedGender = selectedGender,
                onGenderSelected = { selectedGender = it })
            location(location)
            ButtonSignUpDo(
                stringResource(R.string.sign_up), name, email, password, phone, selectedGender,
                showProgress, shoutDownDialogD, shoutDownDialogE, shoutDownDialogV,location,navController
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
                    ), onClick = {   navController.navigate(ScreensRoute.SignIn.route) })
            }
        }
    }
}

@Composable
fun nameField(name: MutableState<String>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp,end=20.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            modifier = Modifier,
            value = name.value,
            onValueChange = { name.value = it },
            placeholder = { Text(text = stringResource(R.string.name)) },
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
fun emailField(email: MutableState<String>) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp,end=20.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            placeholder = { Text(text = stringResource(R.string.email)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.mainColor),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            }
        )
    }
}

@Composable
fun passwordField(password: MutableState<String>) {

    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp,end=20.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            placeholder = { Text(text = stringResource(R.string.password)) },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.mainColor),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation =
            if (passwordVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
            },
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description =
                    if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !(passwordVisible.value) }) {
                    Icon(imageVector = image, description)
                }
            }
        )
    }
}

@Composable
fun confirmPasswordField(conpassword: MutableState<String>) {

    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp,end=20.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            value = conpassword.value,
            onValueChange = { conpassword.value = it },
            placeholder = { Text(text = stringResource(R.string.con_password))},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.mainColor),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            visualTransformation =
            if (passwordVisible.value) VisualTransformation.None
            else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible.value)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff

                // Please provide localized description for accessibility services
                val description =
                    if (passwordVisible.value) "Hide password" else "Show password"

                IconButton(onClick = { passwordVisible.value = !(passwordVisible.value) }) {
                    Icon(imageVector = image, description)
                }
            }
        )
    }
}

@Composable
fun GenderSpinner(
    itemList: List<String>,
    selectedGender: String,
    onGenderSelected: (selectedGender: String) -> Unit,
    // through that we can change value of selectedItem,

) {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=20.dp,end=20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                elevation = 3.dp,
            ) {
                OutlinedButton(
                    modifier = Modifier,
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(10, 10, 10, 10),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment =Alignment.CenterVertically,
                    ){
                        Text(
                            text = selectedGender,
                            modifier = Modifier
                                .weight(8f),
                            color = Color.Gray,
                            fontFamily = FontFamily.Default,
                            fontSize = 15.sp
                        )
                        Icon(
                            modifier=Modifier.weight(1f),
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Color.DarkGray
                        )
                    }
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                //to control dropDownMenu position
                offset = DpOffset(x = (160).dp, y = (5).dp)
            ) {
                itemList.forEach {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                            onGenderSelected(it)
                        }
                    ) {
                        Text(text = it, fontSize = 20.sp, color = Color.DarkGray)
                    }
                }
            }
        }
    }

}

@Composable
fun ButtonSignUpDo(
    buttonName: String,
    name: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    phone: MutableState<String>,
    gender: String,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    location: MutableState<String>,
    navController: NavHostController
) {
    Button(
        onClick = {
            shoutDownProgress.value = true
            Donor_signUp(
                name, email, password, phone, gender, shoutDownProgress,
                showMsgD, showError, showMsgV,location, navController
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp,end=20.dp)
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

@Composable
fun progressBar(show: MutableState<Boolean>) {

    if (show.value) {
        CircularProgressIndicator(
            color = colorResource(id = R.color.mainColor), strokeWidth = 3.dp,
            modifier = Modifier.size(30.dp)
        )
    }

}

@Composable
fun SelectCountryWithCountryCode(phone: MutableState<String>) {

    val countryCode = remember { mutableStateOf("+20") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start=20.dp,end=20.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row() {
            Card(
                modifier = Modifier
                    .height(55.dp)
                    .weight(1f),
                shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp),
                elevation = 3.dp,
            ) {
                CountryCodeChooser(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(10.dp),
                    defaultCountryCode = "20",
                    countryCodeType = CountryCodeType.FLAG,
                    flagSize = DpSize(40.dp,25.dp),
                    onCountyCodeSelected = { code, codeWithPrefix ->
                        countryCode.value = codeWithPrefix
                    }
                )
            }
            Card(
                modifier = Modifier
                    .height(55.dp)
                    .weight(4f),
                shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp),
                elevation = 3.dp,
            ) {
                TextField(
                    value = phone.value,
                    onValueChange = { phone.value = it },
                    placeholder = { Text(text = countryCode.value) },
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
    }

}

@Composable
fun DataDialog(shoutDownDialog: MutableState<Boolean>) {

    if (shoutDownDialog.value) {

        Dialog(onDismissRequest = { shoutDownDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.sure_data_correct),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun ErrorDialog(shoutDownDialog: MutableState<Boolean>) {

    if (shoutDownDialog.value) {

        Dialog(onDismissRequest = { shoutDownDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.error_occurred),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun VerificationDialog(shoutDownDialog: MutableState<Boolean>) {

    if (shoutDownDialog.value) {

        Dialog(onDismissRequest = { shoutDownDialog.value = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Text(
                    text = stringResource(R.string.verify_email),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}