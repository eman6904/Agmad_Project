package com.example.ourproject.screens

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ourproject.R
import com.hbb20.CountryCodePicker
import com.owlbuddy.www.countrycodechooser.CountryCodeChooser
import com.owlbuddy.www.countrycodechooser.utils.enums.CountryCodeType
import org.w3c.dom.Text

@Composable
fun signUp(){
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.White),
        contentAlignment = Alignment.BottomEnd
    ){
        Card(
            modifier=Modifier.size(70.dp),
            shape= RoundedCornerShape(60.dp,0.dp,0.dp,0.dp),
            backgroundColor = colorResource(id = R.color.mainColor),
            elevation = 5.dp
        ){}
    }
    Box(
        Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.TopStart
    ){
        Card(
            modifier=Modifier.size(70.dp),
            shape= RoundedCornerShape(0.dp,0.dp,60.dp,0.dp),
            backgroundColor = colorResource(id = R.color.mainColor),
            elevation = 5.dp
        ){}
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ){
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxHeight()
        ){
            nameField()
            emailField()
            passwordField()
            confirmPasswordField()
            SelectCountryWithCountryCode()
            val Gender = listOf<String>(stringResource(R.string.male), stringResource(R.string.female))
            var selectedGender by rememberSaveable() { mutableStateOf("Gender") }
            GenderSpinner(itemList =Gender , selectedItem =selectedGender , onItemSelected ={selectedGender=it} )
            buttonSignUp(stringResource(R.string.sign_up))
            Row(
                
            ){
                Text(text= stringResource(R.string.have_account))
                Text(text= stringResource(R.string.signin), color= colorResource(id = R.color.mainColor))
            }
        }
    }
}
@Composable
fun nameField() {
    val hint = remember { mutableStateOf("") }
   Box(
       modifier = Modifier
           .fillMaxWidth()
           .background(color = Color.White),
       contentAlignment = Alignment.Center
   ){
       Card(
           modifier = Modifier,
           shape= RoundedCornerShape(10.dp,10.dp,10.dp,10.dp),
           elevation = 3.dp,
       ){
           TextField (
               modifier= Modifier
                   .width(330.dp)
                   .height(50.dp),
               value = hint.value,
               onValueChange = { hint.value = it },
               placeholder = { Text(text = stringResource(R.string.name)) },
               colors = TextFieldDefaults.textFieldColors(
                   backgroundColor = Color.White,
                   focusedIndicatorColor =Color.Transparent,
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
@Composable
fun emailField() {
    val hint = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
            elevation = 3.dp,
        ) {
            TextField(
                modifier = Modifier
                    .width(330.dp)
                    .height(50.dp),
                value = hint.value,
                onValueChange = { hint.value = it },
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
}
@Composable
fun passwordField() {
    val hint = remember { mutableStateOf("") }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
            elevation = 3.dp,
        ) {
            TextField(
                modifier = Modifier
                    .width(330.dp)
                    .height(50.dp),
                value = hint.value,
                onValueChange = { hint.value = it },
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
                visualTransformation=
                if(passwordVisible.value) VisualTransformation.None
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
                    val description = if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible.value = !(passwordVisible.value)}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
        }
    }
}
@Composable
fun confirmPasswordField() {
    val hint = remember { mutableStateOf("") }
    var passwordVisible = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
            elevation = 3.dp,
        ) {
            TextField(
                modifier = Modifier
                    .width(330.dp)
                    .height(50.dp),
                value = hint.value,
                onValueChange = { hint.value = it },
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
                visualTransformation=
                if(passwordVisible.value) VisualTransformation.None
                else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible.value)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description = if (passwordVisible.value) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible.value = !(passwordVisible.value)}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
        }
    }
}
@Composable
fun GenderSpinner(
    itemList: List<String>,
    selectedItem: String,
    onItemSelected: (selectedItem: String) -> Unit,
    // through that we can change value of selectedItem,

) {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
                elevation = 3.dp,
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    shape = RoundedCornerShape(10, 10, 10, 10),
                ) {
                    Text(
                        text = selectedItem,
                        modifier = Modifier
                            .width(280.dp)
                            .padding(8.dp),
                        color = Color.Gray,
                        fontFamily = FontFamily.Default,
                        fontSize = 15.sp
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        tint = Color.DarkGray
                    )
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
                            onItemSelected(it)
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
fun buttonSignUp(buttonName:String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(250.dp)
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
fun SelectCountryWithCountryCode() {
    val hint = remember { mutableStateOf("") }
    val countryCode = remember { mutableStateOf("+20") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
        contentAlignment = Alignment.Center
    ){
        Row(){
            Card(
                modifier = Modifier.height(50.dp),
                shape=RoundedCornerShape(10.dp,0.dp,0.dp,10.dp),
                elevation = 3.dp,
            ){
                CountryCodeChooser(
                    modifier = Modifier.background(color=Color.White).
                    padding(10.dp),
                    defaultCountryCode = "20",
                    countryCodeType = CountryCodeType.FLAG,
                    onCountyCodeSelected = { code, codeWithPrefix ->
                        countryCode.value=codeWithPrefix
                    }
                )
            }
            Card(
                modifier = Modifier,
                shape= RoundedCornerShape(0.dp,10.dp,10.dp,0.dp),
                elevation = 3.dp,
            ){
                TextField (
                    modifier= Modifier
                        .width(290.dp)
                        .height(50.dp),
                    value = hint.value,
                    onValueChange = { hint.value = it },
                    placeholder = { Text(text = countryCode.value) },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                        focusedIndicatorColor =Color.Transparent,
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