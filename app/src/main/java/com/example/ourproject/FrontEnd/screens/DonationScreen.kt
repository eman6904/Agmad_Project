package com.example.ourproject.FrontEnd.screens

import android.Manifest
import android.Manifest.permission.CAMERA
import android.Manifest.permission_group.CAMERA
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder.VideoSource.CAMERA
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.ourproject.BackEnd.Files.*
import com.example.ourproject.BuildConfig
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@Composable
fun donationScreen(navController:NavHostController) {


    var orgList by remember { mutableStateOf(emptyList<String>()) }
     orgList= getOrganizations()
    var org= stringResource(id = R.string.organization)
    var loca= stringResource(id = R.string.location)
    var foodst= stringResource(id = R.string.food_stste)
    val scrollState = rememberScrollState()
    val foodContent= rememberSaveable() { mutableStateOf("")}
    val comment= rememberSaveable() { mutableStateOf("")}
    val mealsNumber= rememberSaveable() { mutableStateOf("")}
    val images= rememberSaveable() { mutableStateOf(false)}
    var selectOrganization by rememberSaveable() { mutableStateOf(org) }
    var selectLocation by rememberSaveable() { mutableStateOf(loca) }
    var selectFoodState by rememberSaveable() { mutableStateOf(foodst) }
    var organization = rememberSaveable() { mutableStateOf("") }
    var location =rememberSaveable() { mutableStateOf("") }
    var foodState = rememberSaveable() { mutableStateOf("") }
    val emptyMealsNumber = rememberSaveable() { mutableStateOf(false)}
    val emptyOrganization = rememberSaveable() { mutableStateOf(false)}
    val emptyLocation = rememberSaveable() { mutableStateOf(false)}
    val emptyFoodState = rememberSaveable() { mutableStateOf(false)}
    val emptyImagesList = rememberSaveable() { mutableStateOf(false)}
    var imagesId =remember { mutableStateOf(emptyList<String>()) }



    val context= LocalContext.current

    val emptyFieldModifier= Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .border(2.dp, Color.Red, RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))

    val notEmptyFieldModifier= Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .border(
            2.dp,
            colorResource(id = R.color.mainColor),
            RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)
        )

    val emptySpinnerModifier= Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .border(2.dp, Color.Red, RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp))
    val notEmptySpinnerModifier= Modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 20.dp, end = 20.dp)
        .border(
            2.dp,
            colorResource(id = R.color.mainColor),
            RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp)
        )

    val emptyImagesListModifier= Modifier
        .padding(5.dp)
        .border(2.dp, Color.Red, shape = CircleShape)
    val notEmptyImagesListModifier=Modifier.padding(5.dp)

   Column(
       modifier=Modifier.fillMaxSize(),
   ) {
       DonationTopBar(navController)
       Column(
           modifier = Modifier
               .padding(bottom = 45.dp)
               .fillMaxSize()
               .verticalScroll(state = scrollState)
               .background(color = Color.White),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally
       ) {

           val foodStateList = listOf<String>(stringResource(R.string.freshfood), stringResource(R.string.leftovers))
           val locationList = listOf<String>(stringResource(R.string.myLocation), stringResource(R.string.new_location))
           if(emptyOrganization.value==false)
            spinner(orgList, selectOrganization, { selectOrganization = it },organization,notEmptySpinnerModifier)
           else
            spinner(orgList, selectOrganization, { selectOrganization = it },organization,emptySpinnerModifier)
           if(emptyLocation.value==false)
              spinner(locationList, selectLocation, { selectLocation = it },location,notEmptySpinnerModifier)
           else
               spinner(locationList, selectLocation, { selectLocation = it },location,emptySpinnerModifier)
           if(emptyFoodState.value==false)
            spinner(foodStateList, selectFoodState, { selectFoodState = it },foodState,notEmptySpinnerModifier)
           else
            spinner(foodStateList, selectFoodState, { selectFoodState = it },foodState,emptySpinnerModifier)
           editText(foodContent, stringResource(id = R.string.foodContent),notEmptyFieldModifier)

           if(emptyImagesList.value==false)
              floatingActionButton(context,images,notEmptyImagesListModifier,imagesId)
           else
              floatingActionButton(context,images,emptyImagesListModifier,imagesId)

           if(emptyMealsNumber.value==false||mealsNumber.value.isNotEmpty())
              editText(mealsNumber, stringResource(R.string.estimatedMealsNumber),notEmptyFieldModifier)
           else
               editText(mealsNumber, stringResource(R.string.estimatedMealsNumber),emptyFieldModifier)
           editText(comment, stringResource(R.string.anyComment),notEmptyFieldModifier)

           requestButton(
               stringResource(R.string.request),
               navController,
               organization.value,
               foodState.value,
               location.value,
               foodContent,
               mealsNumber,
               comment,
               context,
               emptyOrganization,
               emptyLocation,
               emptyFoodState,
               emptyMealsNumber,
               images,
               emptyImagesList,
               imagesId,
               context
           )

       }
   }
}

@Composable
fun DonationTopBar(navController: NavHostController) {
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
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.arrowbackicon),
                                tint = Color.White
                            )
                        }
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

@Composable
fun spinner(
    itemList: List<String>,
    selectedItem: String,
    onItemSelected: (selectedItem: String) -> Unit,
    value:MutableState<String>,
    modifier:Modifier
    // through that we can change value of selectedItem,

) {
    var expanded by rememberSaveable() { mutableStateOf(false) }
    var result = rememberSaveable() { mutableStateOf("") }
    var shoutDown= remember { mutableStateOf(false)}

    value.value=result.value
    if(selectedItem=="New Location"||selectedItem=="إدخال مكان جديد"){

        if(result.value.isEmpty())
         shoutDown.value=true

        newLocation(shoutDown = shoutDown, newLocation = result)
    }else if(selectedItem=="My Location"||selectedItem=="المكان الإفتراضي"){

        result.value= getDonorData().location
    }else{
        result.value=selectedItem
    }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
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
                        text = result.value,
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
fun radioButtonforSelectLanguage(selectedLan:MutableState<String>) {
    val languages = listOf<String>(stringResource(R.string.english),
            stringResource(R.string.arabic)
        )
    val selectedItem = remember { mutableStateOf("") }
    if(selectedItem.value==languages[0])
        selectedLan.value="en"
    else if(selectedItem.value==languages[1])
        selectedLan.value="ar"
    Column() {
        languages.forEach() { item ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding( 10.dp)
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
                Text(text = item, modifier = Modifier.padding(start = 10.dp), fontSize = 20.sp)
            }
        }
    }
}

@Composable
fun checkBox(item: String) {
    var myState = remember { mutableStateOf(false) }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            modifier = Modifier.clip(CircleShape),
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
    foodState:String,
    location:String,
    foodContent:MutableState<String>,
    mealsNumber:MutableState<String>,
    comment:MutableState<String>,
    appContext: Context,
    emptyOrganization:MutableState<Boolean>,
    emptyLocation:MutableState<Boolean>,
    emptyFoodState:MutableState<Boolean>,
    emptyMealsNumber:MutableState<Boolean>,
    images: MutableState<Boolean>,
    emptyImagesList:MutableState<Boolean>,
    imagesId:MutableState<List<String>>,
    context: Context
) {
    var imagesList by remember { mutableStateOf(emptyList<String>()) }
    val _organization= stringResource(id = R.string.organization)
    val _location= stringResource(id = R.string.location)
    val _foodState= stringResource(id = R.string.food_stste)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
    ) {
        Button(
            onClick = {

                emptyOrganization.value = organizationName==_organization

                emptyLocation.value = location==_location

                emptyFoodState.value = foodState==_foodState

                emptyMealsNumber.value = mealsNumber.value.isEmpty()

                emptyImagesList.value = images.value==false

                if(location!=_location&&organizationName!=_organization&&foodState!=_foodState&&mealsNumber.value.isNotEmpty() && images.value){
                           Toast.makeText(appContext, R.string.requestSentSuccessfully,Toast.LENGTH_LONG).show()
                           sendRequest(
                               organizationName,
                               foodState,
                               location,
                               foodContent,
                               mealsNumber,
                               comment,
                               imagesId.value,
                           context)
                       }
                      },
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
fun editText(content: MutableState<String>,hint:String,modifier:Modifier) {

    val focusManager = LocalFocusManager.current
    Card(
        modifier =modifier,
        shape = RoundedCornerShape(10.dp, 10.dp, 10.dp, 10.dp),
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
fun floatingActionButton(context:Context,images:MutableState<Boolean>,modifier: Modifier,
imagesId:MutableState<List<String>>){

    val selectedImage = remember{ mutableStateListOf<Uri?>() }
    var upload = remember{ mutableStateOf(false) }
    if(upload.value==true){

        uploadImage(selectedImage,imagesId)
        Toast.makeText(context, stringResource(R.string.images_are_uploaded),Toast.LENGTH_LONG).show()
        upload.value=false
    }

   //to select  images from gallery
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) {
        selectedImage.apply {

            addAll(it)
            upload.value=true
        }
    }
   ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //to capture image from camera
    val context = LocalContext.current
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(
        Objects.requireNonNull(context),
        BuildConfig.APPLICATION_ID + ".provider", file
    )

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {

            selectedImage.apply {

                add(uri)
                upload.value=true
            }
        }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
            cameraLauncher.launch(uri)
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }




   Column() {

       val shoutDown= remember { mutableStateOf(false)}

       if(shoutDown.value==true) {
           showImages(
               shoutDown = shoutDown,
               imagesId = imagesId
           )
          // shoutDown.value=false
       }
         Text(text= stringResource(R.string.uploadImage),modifier=Modifier.padding(start=20.dp,top=10.dp).fillMaxWidth())
       Row(
           horizontalArrangement = Arrangement.Center,
           modifier = Modifier
               // .padding(20.dp)
               .fillMaxWidth()
       ){
           FloatingActionButton(
               onClick = {
                           launcher.launch("image/*")
                         },
               modifier=modifier,
               backgroundColor = colorResource(id = R.color.mainColor)
           ) {
               Icon(imageVector = Icons.Filled.Upload, contentDescription ="",tint=Color.White )
           }
           //
           FloatingActionButton(
               onClick = {

                   val permissionCheckResult =
                       ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                   if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {

                       cameraLauncher.launch(uri)

                   } else {
                       // Request a permission
                       permissionLauncher.launch(Manifest.permission.CAMERA)
                   }

               },
               modifier=Modifier.padding(5.dp),
               backgroundColor = colorResource(id = R.color.mainColor)
           ) {
               Icon(imageVector = Icons.Filled.CameraAlt, contentDescription ="",tint=Color.White )
           }

       }
       if(images.value==true){
           ClickableText(text = AnnotatedString(stringResource(R.string.show_images)) ,
               onClick ={shoutDown.value=true },
               modifier=Modifier.padding(start=20.dp,top=10.dp),
               style= TextStyle(color= colorResource(id = R.color.mainColor), textDecoration = TextDecoration.Underline)
           )
       }
       if(selectedImage.isNotEmpty()){

           images.value=true

       }
//       if (capturedImageUri.path?.isNotEmpty() == true) {
//           AsyncImage(
//               modifier = Modifier.size(size = 240.dp),
//               model = capturedImageUri,
//               contentDescription = null
//           )
//       }
   }
}
@Composable
fun newLocation(shoutDown: MutableState<Boolean>,newLocation:MutableState<String>) {

    val location = rememberSaveable() { mutableStateOf("") }
    newLocation.value=location.value
    if (shoutDown.value) {
        Dialog(
            onDismissRequest = { shoutDown.value = false }
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
                        value = location.value,
                        onValueChange = { location.value = it },
                        modifier = Modifier.padding(20.dp),
                        label = { Text(text = stringResource(R.string.enterNewLocation)) },
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
                            shoutDown.value = false
                        },
                        content = {
                            Text(
                                text = stringResource(R.string.done),
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
@Composable
fun showImages(shoutDown: MutableState<Boolean>,imagesId:MutableState<List<String>>){

    var imageUris by remember { mutableStateOf(emptyList<String>()) }
    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid

    imageUris= getImages(imagesId = imagesId.value,currentUserId)
   if(shoutDown.value) {
       Dialog(
           onDismissRequest = { shoutDown.value = false }
       ) {
           Card(
               modifier = Modifier
                   .fillMaxSize()
           ) {
               Column(
                   modifier = Modifier.fillMaxSize()
               ) {

                   if (imageUris.size == 0) {

                       Box(
                           modifier = Modifier.fillMaxSize(),
                           contentAlignment = Alignment.Center
                       ) {
                           Text(text = stringResource(R.string.waiting), color = Color.Gray)
                       }
                   } else {

                       LazyColumn(

                           modifier = Modifier.fillMaxSize()
                       ) {

                           items(items = imageUris, itemContent = { item ->
                               AsyncImage(
                                   modifier = Modifier.padding(20.dp),
                                   model = item,
                                   contentDescription = null
                               )
                               Spacer(modifier = Modifier.width(10.dp))
                           })
                       }
                   }

               }
           }
       }
   }
}