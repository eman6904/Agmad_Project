package com.example.ourproject.FrontEnd.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.DonorItems
import com.example.ourproject.BackEnd.DataClasses.OrganizationItems
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getImages
import com.example.ourproject.BackEnd.Files.getOrganizations
import com.example.ourproject.BackEnd.Files.sendRequest
import com.example.ourproject.BackEnd.Files.uploadImage
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

@Composable
fun donationScreen(navController:NavHostController) {


    var orgList by remember { mutableStateOf(emptyList<String>()) }
     orgList= getOrganizations()
    val scrollState = rememberScrollState()
    val foodContent= rememberSaveable() { mutableStateOf("")}
    val comment= rememberSaveable() { mutableStateOf("")}
    val mealsNumber= rememberSaveable() { mutableStateOf("")}
    var showImages= rememberSaveable() { mutableStateOf(false)}
    var selectOrganization by rememberSaveable() { mutableStateOf("Organization") }
    var selectLocation by rememberSaveable() { mutableStateOf("Food state") }
    var selectFoodState by rememberSaveable() { mutableStateOf("Location") }


   Column(
       modifier=Modifier.fillMaxSize(),
   ) {
       DonationTopBar()
       Column(
           modifier = Modifier
               .padding(bottom = 45.dp)
               .fillMaxSize()
               .verticalScroll(state = scrollState)
               .background(color = Color.White),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {

           val options = listOf<String>("organization", "location", "food state")
           spinner(orgList, selectOrganization, { selectOrganization = it })
           spinner(options, selectLocation, { selectLocation = it })
           spinner(options, selectFoodState, { selectFoodState = it })
           editText(foodContent,"Food Content")
           floatingActionButton(navController = navController,showImages)
           editText(mealsNumber,"Estimated Meals Number")
           editText(comment,"Any Comment?")
           requestButton(
               stringResource(R.string.request),
               navController,
               selectOrganization,
               selectFoodState,
               selectLocation,
               foodContent,
               mealsNumber,
               comment,
           )

       }
   }
}

@Composable
fun DonationTopBar() {
    Card(
        modifier = Modifier
            .background(color = Color.White)
            .height(102.dp)
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
                        .fillMaxSize()
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
        border = BorderStroke(
            2.dp,
            color = colorResource(id = R.color.mainColor)
        ),
        elevation = 3.dp,
    ) {
        Column() {
            OutlinedButton(
                onClick = { expanded = true },
                //shape = RoundedCornerShape(30, 30, 30, 30),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment =Alignment.CenterVertically,
                ){
                    Text(
                        text = selectedItem,
                        modifier = Modifier
                            .weight(8f)
                            .height(35.dp),
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
                            onItemSelected(it)
                        }
                    ) {
                        Text(text = it, fontSize = 20.sp, color = Color.DarkGray)
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
fun requestButton(
    buttonName: String, navController: NavHostController,
    organizationName:String,
    foodState:String ,
    location:String,
    foodContent:MutableState<String>,
    mealsNumber:MutableState<String>,
    comment:MutableState<String>,
) {
    var imagesList by remember { mutableStateOf(emptyList<String>()) }
    imagesList= getImages()
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Button(
            onClick = {
                sendRequest(
                organizationName,
                foodState,
                location,
                foodContent,
                mealsNumber,
                comment,
                imagesList)},
            modifier = Modifier
                .fillMaxWidth(),
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
fun editText(content: MutableState<String>,hint:String) {

    val focusManager = LocalFocusManager.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        shape = RoundedCornerShape(20.dp, 0.dp, 20.dp, 0.dp),
        elevation = 5.dp,
    ) {
        TextField(
            modifier = Modifier,
            value = content.value,
            onValueChange = { content.value = it },
            placeholder = { Text(text=hint)},
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = colorResource(id = R.color.mainColor),
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.Transparent

            ),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            trailingIcon = {
                if(content.value!=""){
                    IconButton(
                        onClick = {focusManager.clearFocus()}
                    ) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = stringResource(R.string.arrowbackicon),
                            tint = colorResource(id = R.color.mainColor),
                        )
                    }
                }
            }
        )
    }
}
@Composable
fun floatingActionButton(
 navController:NavHostController,
 showImages:MutableState<Boolean>)
{

    val selectedImage = remember{ mutableStateListOf<Uri?>() }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selectedImage.apply {
            clear()
            addAll(it)
            uploadImage(selectedImage)
        }
    }


    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid
    val listRef = FirebaseStorage.getInstance().getReference().child(currentUserId+"/")
    listRef.listAll().addOnSuccessListener { listResult ->
        for (file in listResult.items) {
            showImages.value = true
        }
    }

   Column() {
       Text(text="Upload Image:",modifier=Modifier.padding(start=20.dp,top=10.dp))
       Row(
           horizontalArrangement = Arrangement.Center,
           modifier = Modifier
               // .padding(20.dp)
               .fillMaxWidth()
       ){
           FloatingActionButton(
               onClick = {
                           launcher.launch("image/jpeg")
                         },
               modifier=Modifier.padding(5.dp),
               backgroundColor = colorResource(id = R.color.mainColor)
           ) {
               Icon(imageVector = Icons.Filled.Upload, contentDescription ="",tint=Color.White )
           }
           FloatingActionButton(
               onClick = {},
               modifier=Modifier.padding(5.dp),
               backgroundColor = colorResource(id = R.color.mainColor)
           ) {
               Icon(imageVector = Icons.Filled.CameraAlt, contentDescription ="",tint=Color.White )
           }
       }
       if(showImages.value){
          ClickableText(
              text = AnnotatedString("Show Images"),
              onClick ={navController.navigate(ScreensRoute.FoodContentImages.route)} ,
              modifier=Modifier.padding(start=20.dp,top=10.dp),
              style = TextStyle(color= colorResource(id = R.color.green))
          )
       }
   }
}
