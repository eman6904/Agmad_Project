package com.example.ourproject.FrontEnd.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material.icons.rounded.StarHalf
import androidx.compose.material.icons.rounded.StarOutline
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ourproject.BackEnd.Files.*
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.MainActivity
import com.example.ourproject.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


@Composable
fun donorHistory(navController: NavHostController) {


    val acceptedRequestedNumber = rememberSaveable { mutableStateOf(0) }
    val rejectedRequestedNumber = rememberSaveable { mutableStateOf(0) }

    val accList = myRequests(typeInArabic = "مقبول", typeInEnglish = "Accepted")
    val rejList = myRequests(typeInArabic = "مرفوض", typeInEnglish = "Rejected")

    acceptedRequestedNumber.value = accList.size
    rejectedRequestedNumber.value = rejList.size

    val selectedLevel = selectLevel(acceptedRequestedNumber.value)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HistoryTopBar(navController, "Donors")

        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            if (getDonorData().profileImage == "") {
                Image(
                    painterResource(R.drawable.user_icon),
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .weight(1f)
                        .padding(10.dp),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(colorResource(id = R.color.mainColor))
                )
            } else {
                AsyncImage(
                    model = getDonorData().profileImage,
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .weight(1f)
                        .padding(10.dp),
                    contentDescription = "",
                    // colorFilter = ColorFilter.tint(colorResource(id = R.color.mainColor))
                )
            }
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(10.dp)
            ) {

                Text(
                    text = getDonorData().name, fontSize = 20.sp, fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    modifier = Modifier.padding(3.dp)
                )
                Row(
                    modifier = Modifier.padding(3.dp)
                ) {
                    Icon(
                        painterResource(id = R.drawable.id_icon),
                        null,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = getDonorData().phone,
                        modifier = Modifier
                            .weight(12f)
                            .padding(start = 5.dp)
                    )
                }
                Row(
                    modifier = Modifier.padding(3.dp)
                ) {
                    Icon(imageVector = Icons.Default.MyLocation, null)
                    Text(text = getDonorData().location, modifier = Modifier.padding(start = 5.dp))
                }
                ratingBar(2.5)
            }
        }

        Text(
            text = stringResource(R.string.my_impact), fontStyle = FontStyle.Normal,
            fontFamily = FontFamily(Font(R.font.bold)),
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp, start = 10.dp),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(IntrinsicSize.Min)
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = stringResource(R.string.total_donations),
                    color = Color.Black,
                    modifier = Modifier.padding(10.dp),
                )
                Text(
                    text = "${acceptedRequestedNumber.value + rejectedRequestedNumber.value}",
                    fontStyle = FontStyle.Normal,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    fontSize = 30.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 10.dp),
                )
            }
            Divider(
                modifier = Modifier
                    .width(2.dp)
                    .padding(top = 20.dp)
                    .weight(0.02f)
                    .fillMaxHeight(),
                color = Color.Gray
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(top = 20.dp, end = 25.dp)
            ) {
                Text(
                    text = " ${rejectedRequestedNumber.value}",
                    color = Color.Red,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color.White,
                    thickness = 5.dp
                )
                Text(
                    text = " ${acceptedRequestedNumber.value}",
                    color = Color.Green,
                    fontFamily = FontFamily(Font(R.font.bold)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.LightGray)
                )
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 10.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.rejected_requests),
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .weight(20f)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = Icons.Default.Circle,
                        contentDescription = null,
                        tint = Color.Green,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = stringResource(R.string.accepted_requests),
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .weight(20f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(72.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(
                onClick = { navController.navigate(ScreensRoute.Levels.route) },
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = null,
                    tint = colorResource(id = selectedLevel.color)
                )
                Text(
                    text = stringResource(R.string.my_level),
                    modifier = Modifier.padding(start = 12.dp),
                    color = Color.Black
                )
            }

            OutlinedButton(
                onClick = { navController.navigate(ScreensRoute.Plant.route) },
                modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Icon(
                    painterResource(id = R.drawable.plant),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = colorResource(id = R.color.mainColor)
                )
                Text(
                    text = stringResource(R.string.my_plant),
                    modifier = Modifier.padding(start = 12.dp),
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun HistoryTopBar(navController: NavHostController, selectedUser: String) {

    var showMenu = rememberSaveable { mutableStateOf(false) }
    var selectLanguage = rememberSaveable { mutableStateOf(false) }
    var language = rememberSaveable { mutableStateOf("") }
    val profileImageDialog = remember { mutableStateOf(false) }

    //for profile image
    if (profileImageDialog.value)
        changeProfileImage(shutdown = profileImageDialog, selectedUser = selectedUser)

    ////////////////////////////////////////////////////////////////////////////////////////////////
    // for language
    if (selectLanguage.value == true) {

        languageDialog(selectLanguage, language)
        if (language.value.isNotEmpty())
            MainActivity.sharedPreferences.edit()
                .putString(MainActivity.SELECTED_LANGUAGE, language.value).apply()
        //and look at main activity
    }
    // Load the saved language and apply it

    if (language.value.isNotEmpty()) {

        setLocale1(lang = language.value)
    }
    menuItems2(showMenu, selectLanguage, navController)
    ////////////////////////////////////////////////////////////////////////////////////////////////
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
                    title = {
                        Text(
                            text = stringResource(R.string.history),
                            color = Color.White,
                            modifier = Modifier.padding(start = 10.dp)
                        )
                    },
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
                                profileImageDialog.value = !profileImageDialog.value
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = "menu icon",
                                tint = Color.White
                            )
                        }
                        IconButton(
                            onClick = {
                                showMenu.value = !showMenu.value
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
fun ratingBar(rating: Double = 0.0) {

    Row() {

        var isHalfStar = (rating % 1) != 0.0
        for (index in 1..5) {

            Icon(
                contentDescription = null,
                tint = colorResource(id = R.color.yellow),
                imageVector = if (index <= rating) {
                    Icons.Rounded.Star
                } else {

                    if (isHalfStar) {

                        isHalfStar = false
                        Icons.Rounded.StarHalf
                    } else {
                        Icons.Rounded.StarOutline
                    }
                }
            )
        }
    }
}

@Composable
fun changeProfileImage(shutdown: MutableState<Boolean>, selectedUser: String) {

    val selectedImage = remember { mutableStateOf<Uri?>(null) }
    var upload = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (upload.value == true) {

        updateProfileImage(selectedImage, selectedUser)
        Toast.makeText(context, stringResource(R.string.images_are_uploaded), Toast.LENGTH_LONG)
            .show()
        upload.value = false
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        selectedImage.apply {
            Log.d("uriiiiiiiiiiiiiiii", it.toString())
            if (it != null) {
                selectedImage.value = it
                upload.value = true
            }
        }
    }
    if (shutdown.value) {

        Dialog(
            onDismissRequest = { shutdown.value = false }
        ) {
            Card(
                shape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 20.dp),
                elevation = 10.dp,
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Max)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        ClickableText(
                            AnnotatedString(stringResource(R.string.set_new_image)),
                            onClick = {
                                launcher.launch("image/*")
                                //  shutdown.value = false
                            })
                    }

                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                            .fillMaxWidth()
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        ClickableText(
                            AnnotatedString(stringResource(R.string.unsetImage)),
                            onClick = {

                                var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid
                                var obj = FirebaseDatabase.getInstance().getReference(selectedUser)
                                    .child(currentUserId)
                                val hashMap: HashMap<String, Any> = HashMap()
                                hashMap.put("profileImage", "")
                                obj?.updateChildren(hashMap as Map<String, Any>)
                                    ?.addOnSuccessListener {

                                    }?.addOnFailureListener {

                                    }
                            })
                    }
                }
            }
        }
    }
}

