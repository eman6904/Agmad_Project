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
import com.example.ourproject.R
import com.example.ourproject.FrontEnd.ScreensRoute
import com.owlbuddy.www.countrycodechooser.CountryCodeChooser
import com.owlbuddy.www.countrycodechooser.utils.enums.CountryCodeType

@Composable
fun DonorSignUp(navController: NavHostController) {

    val genderL = stringResource(id = R.string.gender)
    val name = rememberSaveable() { mutableStateOf("") }
    val email = rememberSaveable() { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val location = rememberSaveable { mutableStateOf("") }
    val conPassword = rememberSaveable { mutableStateOf("") }
    val showProgress = rememberSaveable() { mutableStateOf(false) }
    val phone = rememberSaveable() { mutableStateOf("") }
    var selectedGender by rememberSaveable() { mutableStateOf(genderL) }
    var shoutDownDialogD1 = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogD2 = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogE = rememberSaveable() { mutableStateOf(false) }
    var shoutDownDialogV = rememberSaveable() { mutableStateOf(false) }
    var emptyPassword = rememberSaveable() { mutableStateOf(false) }
    var emptyPhone = rememberSaveable() { mutableStateOf(false) }
    var emptyEmail = rememberSaveable() { mutableStateOf(false) }
    var emptyConPassword = rememberSaveable() { mutableStateOf(false) }
    var emptyName = rememberSaveable() { mutableStateOf(false) }
    var emptyLocation = rememberSaveable() { mutableStateOf(false) }
    var emptyGender = rememberSaveable() { mutableStateOf(false) }

    val modifierForEmptyField = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp)
        .height(55.dp)
        .border(2.dp, Color.Red, RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
    val modifierForNotEmptyField = Modifier
        .fillMaxWidth()
        .padding(start = 20.dp, end = 20.dp)
        .height(55.dp)
    val modifierForEmptyPhoneField = Modifier
        .fillMaxWidth()
        .height(55.dp)
        .border(2.dp, Color.Red, RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp))
    val modifierForNotEmptyPhoneField = Modifier
        .fillMaxWidth()
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
            Text(
                text = stringResource(R.string.donor_sign_up),
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
            if (emptyPhone.value == false || phone.value.isNotEmpty())
                SelectCountryWithCountryCode(phone, modifierForNotEmptyPhoneField)
            else
                SelectCountryWithCountryCode(phone, modifierForEmptyPhoneField)
            val Gender =
                listOf<String>(stringResource(R.string.male), stringResource(R.string.female))

            if (emptyGender.value == false || selectedGender != "Gender")
                GenderSpinner(
                    itemList = Gender,
                    selectedGender = selectedGender,
                    onGenderSelected = { selectedGender = it }, modifierForNotEmptyField
                )
            else
                GenderSpinner(
                    itemList = Gender,
                    selectedGender = selectedGender,
                    onGenderSelected = { selectedGender = it }, modifierForEmptyField
                )

            if (emptyLocation.value == false || location.value.isNotEmpty())
                location(location, modifierForNotEmptyField)
            else
                location(location, modifierForEmptyField)

            ButtonSignUpDo(
                stringResource(R.string.sign_up),
                name,
                email,
                password,
                conPassword,
                phone,
                selectedGender,
                showProgress,
                shoutDownDialogD1,
                shoutDownDialogE,
                shoutDownDialogV,
                location,
                navController,
                emptyPassword,
                emptyConPassword,
                emptyName,
                emptyPhone,
                emptyEmail,
                emptyLocation,
                emptyGender
            )
            progressBar(showProgress)
            ErrorDialog(shoutDownDialog = shoutDownDialogE)
            signUpError(shoutDownDialog = shoutDownDialogD1)
            signInError(shoutDownDialog = shoutDownDialogD2)
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
}

@Composable
fun nameField(name: MutableState<String>, modifier: Modifier) {

    Card(
        modifier = modifier,
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
fun emailField(email: MutableState<String>, modifier: Modifier) {

    Card(
        modifier = modifier,
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
fun passwordField(password: MutableState<String>, modifier: Modifier) {

    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier,
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
fun confirmPasswordField(conpassword: MutableState<String>, modifier: Modifier) {

    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        elevation = 3.dp,
    ) {
        TextField(
            value = conpassword.value,
            onValueChange = { conpassword.value = it },
            placeholder = { Text(text = stringResource(R.string.con_password)) },
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
    modifier: Modifier
    // through that we can change value of selectedItem,

) {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
        ) {
            Card(
                modifier = modifier,
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
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = selectedGender,
                            modifier = Modifier
                                .weight(8f),
                            color = Color.Gray,
                            fontFamily = FontFamily.Default,
                            fontSize = 15.sp
                        )
                        Icon(
                            modifier = Modifier.weight(1f),
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
    conPassword: MutableState<String>,
    phone: MutableState<String>,
    gender: String,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    location: MutableState<String>,
    navController: NavHostController,
    emptyPassword: MutableState<Boolean>,
    emptyConPassword: MutableState<Boolean>,
    emptyName: MutableState<Boolean>,
    emptyPhone: MutableState<Boolean>,
    emptyEmail: MutableState<Boolean>,
    emptyLocation: MutableState<Boolean>,
    emptyGender: MutableState<Boolean>
) {

    Button(
        onClick = {

            if (password.value.isEmpty()) {
                emptyPassword.value = true

            } else
                emptyPassword.value = false

            if (conPassword.value.isEmpty() || conPassword.value != password.value) {
                emptyConPassword.value = true

            } else {
                emptyConPassword.value = false
            }

            if (phone.value.isEmpty()) {
                emptyPhone.value = true

            } else {
                emptyPhone.value = false
            }

            if (email.value.isEmpty()) {
                emptyEmail.value = true

            } else {
                emptyEmail.value = false
            }

            if (location.value.isEmpty()) {
                emptyLocation.value = true;

            } else {
                emptyLocation.value = false
            }

            if (gender == "Gender") {
                emptyGender.value = true;

            } else {
                emptyGender.value = false
            }

            if (name.value.isEmpty()) {
                emptyName.value = true;

            } else {
                emptyName.value = false
            }
            if (name.value.isNotEmpty() && email.value.isNotEmpty() && password.value.isNotEmpty()
                && conPassword.value.isNotEmpty() && phone.value.isNotEmpty() && location.value.isNotEmpty() && gender.isNotEmpty()
                && conPassword.value == password.value
            ) {
                shoutDownProgress.value = true
                Donor_signUp(
                    name, email, password, phone, gender, shoutDownProgress,
                    showMsgD, showError, showMsgV, location, navController
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
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
fun SelectCountryWithCountryCode(phone: MutableState<String>, modifier: Modifier) {

    val countryCode = remember { mutableStateOf("+20") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp)
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Row() {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp, 0.dp, 0.dp, 10.dp),
                elevation = 3.dp,
            ) {
                CountryCodeChooser(
                    modifier = Modifier
                        .background(color = Color.White)
                        .padding(10.dp),
                    defaultCountryCode = "20",
                    countryCodeType = CountryCodeType.FLAG,
                    flagSize = DpSize(40.dp, 25.dp),
                    onCountyCodeSelected = { code, codeWithPrefix ->
                        countryCode.value = codeWithPrefix
                    }
                )
            }
            Card(
                modifier = modifier
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
fun signInError(shoutDownDialog: MutableState<Boolean>) {

    val scrollState = rememberScrollState()
    if (shoutDownDialog.value) {

        Dialog(
            onDismissRequest = { shoutDownDialog.value = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .verticalScroll(state = scrollState),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 10.dp)
                        .fillMaxSize()
                ) {

                    Text(
                        text = stringResource(R.string.sure_data_correct),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = stringResource(R.string.internet_connection),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        FloatingActionButton(
                            onClick = { shoutDownDialog.value = false },
                            modifier = Modifier.padding(10.dp),
                            backgroundColor = Color.Red
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun signUpError(shoutDownDialog: MutableState<Boolean>) {

    val scrollState = rememberScrollState()
    if (shoutDownDialog.value) {

        Dialog(
            onDismissRequest = { shoutDownDialog.value = false }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp)
                    .verticalScroll(state = scrollState),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 30.dp, bottom = 10.dp)
                        .fillMaxSize()
                ) {

                    Text(
                        text = stringResource(R.string.validationdata),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = stringResource(R.string.internet_connection),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = stringResource(R.string.already_have_an_account),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(3.dp)
                            .wrapContentSize(Alignment.Center),
                        textAlign = TextAlign.Center,
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomCenter
                    ) {
                        FloatingActionButton(
                            onClick = { shoutDownDialog.value = false },
                            modifier = Modifier.padding(10.dp),
                            backgroundColor = Color.Red
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = "",
                                tint = Color.White
                            )
                        }
                    }
                }
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