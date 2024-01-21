package com.example.ourproject.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ourproject.R

@Composable
fun donationScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
            .background(color = Color.White)
    ) {
        topBar()
        val options = listOf<String>("organization", "location", "food state")
        var selectOrganization by rememberSaveable() { mutableStateOf("Organization") }
        spinner(options, selectOrganization, { selectOrganization = it })
        var selectLocation by rememberSaveable() { mutableStateOf("Food state") }
        spinner(options, selectLocation, { selectLocation = it })
        var selectFoodState by rememberSaveable() { mutableStateOf("Location") }
        spinner(options, selectFoodState, { selectFoodState = it })
        Text(
            text = stringResource(R.string.food_size),
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(10.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            radioButton()
            textField()
        }
        Text(
            text = stringResource(R.string.food_content),
            color = Color.Black,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 20.dp)
        )
        val foodOptions = listOf(
            stringResource(R.string.meat),
            stringResource(R.string.eggs),
            stringResource(R.string.dairy),
            stringResource(R.string.dry),
            stringResource(R.string.iced),
            stringResource(R.string.produce),
            stringResource(R.string.beverages),
            stringResource(R.string.backed_goods),
            stringResource(R.string.non_perishable)
        )
        foodOptions.forEach() { checkBox(item = it) }
        button(stringResource(R.string.request))
    }
}

@Composable
fun topBar() {
    Card(
        modifier = Modifier
            .background(color = Color.White)
            .height(102.dp),
        shape = RoundedCornerShape(
            topEnd = 0.dp,
            topStart = 0.dp,
            bottomEnd = 30.dp,
            bottomStart = 30.dp
        ),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 10.dp
    ) {
        Scaffold(
            Modifier.height(110.dp),
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.donation), color = Color.White) },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.arrowbackicon),
                                tint = Color.White
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(R.string.search_icon),
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = stringResource(R.string.locationIcon),
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = stringResource(R.string.menuIcon),
                                tint = Color.White
                            )
                        }
                    },
                    backgroundColor = colorResource(id = R.color.mainColor),
                    modifier = Modifier
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(0, 0, 30, 30))
                )
            }
        ) {}
    }
}

@Composable
fun spinner(
    itemList: List<String>,
    selectedItem: String,
    onItemSelected: (selectedItem: String) -> Unit,
    // through that we can change value of selectedItem,

) {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column() {
            OutlinedButton(
                onClick = { expanded = true },
                shape = RoundedCornerShape(30, 30, 30, 30),
                border = BorderStroke(
                    2.dp,
                    color = colorResource(id = R.color.mainColor)
                )
            ) {
                Text(
                    text = selectedItem,
                    modifier = Modifier
                        .width(300.dp)
                        .padding(8.dp),
                    color = Color.DarkGray,
                    fontFamily = FontFamily.Default,
                    fontSize = 15.sp
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
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
fun radioButton() {
    val radioButtons = listOf<String>(
        stringResource(R.string.small), stringResource(R.string.medium), stringResource(
            R.string.large
        ), stringResource(R.string.x_large)
    )
    val selectedItem = remember { mutableStateOf("") }
    Row() {
        radioButtons.forEach() { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                RadioButton(
                    modifier = Modifier.size(15.dp),
                    selected = item == selectedItem.value,
                    onClick = { selectedItem.value = item },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(id = R.color.mainColor),
                        unselectedColor = colorResource(id = R.color.mainColor),
                        disabledColor = Color.DarkGray
                    )
                )
                Text(text = item, modifier = Modifier.padding(start = 5.dp), fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun checkBox(item: String) {
    var myState = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = Modifier,
            checked = myState.value,
            onCheckedChange = { myState.value = it },
            colors = CheckboxDefaults.colors(
                uncheckedColor = colorResource(id = R.color.mainColor),
                checkedColor = colorResource(id = R.color.mainColor),
                checkmarkColor = Color.White,
            )
        )
        Text(text = item)
    }
}

@Composable
fun textField() {
    val hint = remember { mutableStateOf("") }
    OutlinedTextField(
        value = hint.value,
        onValueChange = { hint.value = it },
        modifier = Modifier.padding(bottom = 20.dp, start = 15.dp, end = 10.dp),
        label = { Text(text = stringResource(R.string.enter_weight)) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White,
            focusedIndicatorColor = colorResource(id = R.color.mainColor),
            focusedLabelColor = colorResource(id = R.color.mainColor),
            unfocusedIndicatorColor = colorResource(id = R.color.mainColor),
            unfocusedLabelColor = colorResource(id = R.color.mainColor),
            cursorColor = colorResource(id = R.color.mainColor)
        ),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun button(buttonName: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(330.dp)
                .padding(bottom = 80.dp, top = 10.dp),
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