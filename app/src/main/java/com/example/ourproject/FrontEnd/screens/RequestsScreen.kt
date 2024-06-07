package com.example.ourproject.FrontEnd.screens

import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.BackEnd.Files.getAcceptedRequested
import com.example.ourproject.BackEnd.Files.getRejectedRequested
import com.example.ourproject.BackEnd.Files.getRequests
import com.example.ourproject.BackEnd.Files.updateRequest
import com.example.ourproject.FrontEnd.ScreensRoute
import com.example.ourproject.R

@Composable
fun requests(navController:NavHostController,type:String){

    val context= LocalContext.current

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }

    val requests=stringResource(id = R.string.requests)
    val accepted_requests=stringResource(id = R.string.acceptedRequests)
    val rejected_requests=stringResource(id = R.string.rejectedRequests)


    when(type){

        requests-> requestsList = getRequests()
        accepted_requests-> requestsList = getAcceptedRequested()
        rejected_requests-> requestsList = getRejectedRequested()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        requestsTopBar(navController,type)

        if(requestsList.isEmpty()){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Text(text = stringResource(R.string.no_items),color=Color.Gray, fontSize = 15.sp)
            }
        }else{
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                itemsIndexed(items = requestsList){ index,request->
                    requestItem(
                        index,
                        requestsList,
                        navController,
                        type,
                        context
                    )
                }
            }
        }
    }
}
@Composable
fun requestItem(index:Int,requests:List<RequestItems>,navController: NavHostController,requestType:String,context: Context){

    var currRequest = remember { mutableStateOf(RequestItems())}
    val shoutDownDialog= remember { mutableStateOf(false)}
    if(shoutDownDialog.value)
     requestDisplay(shoutDownDialog = shoutDownDialog, request = currRequest, navController, requestType, context )
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                currRequest.value = requests[index]
                shoutDownDialog.value = true
            },
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp
    ){
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
        ){
            Icon(
                painter = painterResource(id = R.drawable.food_donation_icon2),
                contentDescription = "navigation icon",
                tint= colorResource(id = R.color.mainColor),
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
            )
            Column (
                   modifier = Modifier
                       .weight(5f)
                       .padding(5.dp)
                    ){
                Text(
                    text=requests[index].donorName,
                    modifier = Modifier
                )
                Text(
                    text=requests[index].donorPhone,
                    modifier = Modifier
                )
                Text(
                    text=requests[index].date_timeOfRequest,
                    modifier = Modifier,
                    color= Color.Gray
                )

            }
        }
    }
}
@Composable
fun requestsTopBar(navController: NavHostController,title:String) {
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
                    title = {
                        Text(
                            text = title,
                            color = Color.White
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = stringResource(R.string.arrowbackicon),
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
fun requestDisplay(
    shoutDownDialog:MutableState<Boolean>,
    request:MutableState<RequestItems>,
    navController: NavHostController,
    requestsType:String,
    context: Context
) {

    var requestStatus= rememberSaveable{ mutableStateOf(request.value.status)}
    var hint= rememberSaveable{ mutableStateOf("")}
    var showDialogForOrganizationResponse= rememberSaveable{ mutableStateOf(false)}
    if(showDialogForOrganizationResponse.value)
        organizationResponse(showDialogForOrganizationResponse,hint,requestStatus,request.value.requestId,context)
    val scrollState = rememberScrollState()
    val rejectedReason=stringResource(id = R.string.ReasonForRejectedRequest)
    val acceptResponse=stringResource(id = R.string.dateTimeForReceiveFood)
    val accepted=stringResource(id = R.string.accepted)
    val rejected=stringResource(id = R.string.rejected)
    val confirmDialog= remember { mutableStateOf(false)}
    val warningsText= stringResource(R.string.warningText)
    val btnName1= stringResource(R.string.cancel)
    val btnName2= stringResource(R.string.deletRequest)
    if(confirmDialog.value)
        deleteConfirming(shoutDownDialog = shoutDownDialog,request,warningsText,btnName1,btnName2)
    Dialog(
        onDismissRequest = { shoutDownDialog.value = false }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state = scrollState),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column() {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd
                ){
                    IconButton(onClick = {
                       confirmDialog.value=true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.namee),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    Text(request.value.donorName)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.phone),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    Text(request.value.donorPhone)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.locationn),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    Text(request.value.location)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.mealsNumber),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    Text(request.value.mealNumber)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.foodContentt),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    Text(request.value.foodContent)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.imageListt),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    ClickableText(
                        AnnotatedString(stringResource(id = R.string.foodContent)),
                        onClick = {
                                  navController.navigate(ScreensRoute.RequestImages.route+"/"+request.value.requestId+"/"+requestsType)
                        },
                        style = TextStyle(
                            color=Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
               if(request.value.status!=""){
                   Row(
                       modifier = Modifier.padding(15.dp)
                   ) {
                       Text(
                           text = stringResource(R.string.statuss),
                           fontFamily = FontFamily(Font(R.font.bold)),
                           modifier=Modifier.padding(end=5.dp)
                       )
                       if(requestStatus.value=="Accepted"||requestStatus.value=="مقبول")
                           Text(requestStatus.value,color=Color.Green)
                       else
                           Text(requestStatus.value,color=Color.Red)
                   }
               }
                if(request.value.organizationResponse.isNotEmpty()){
                    Row(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.organizationResponse),
                            fontFamily = FontFamily(Font(R.font.bold)),
                            modifier=Modifier.padding(end=5.dp)
                        )
                        Text(request.value.organizationResponse)
                    }
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.comment),
                        fontFamily = FontFamily(Font(R.font.bold)),
                        modifier=Modifier.padding(end=5.dp)
                    )
                    Text(request.value.comment)
                }
               if(request.value.status==""){
                   Row(
                       modifier = Modifier
                           .padding(10.dp)
                           .fillMaxWidth(),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.Center
                   ){
                       Button(
                           onClick = {
                               requestStatus.value=rejected
                               hint.value= rejectedReason
                               showDialogForOrganizationResponse.value=true
                           },
                           colors = ButtonDefaults.buttonColors(
                               contentColor = Color.White,
                               backgroundColor =Color.Red),
                           modifier = Modifier.padding(5.dp)
                       ) {
                           Text(text= stringResource(R.string.reject))
                       }
                       Button(
                           onClick = {
                               requestStatus.value=accepted
                               hint.value= acceptResponse
                               showDialogForOrganizationResponse.value=true
                           },
                           colors = ButtonDefaults.buttonColors(
                               contentColor = Color.White,
                               backgroundColor = colorResource(id = R.color.green)),
                           modifier = Modifier.padding(5.dp)
                       ) {
                           Text(text= stringResource(R.string.accept))
                       }
                   }

               }
            }
        }
    }
}
@Composable
fun organizationResponse(shoutDownDialog: MutableState<Boolean>,
   hint:MutableState<String>,
   requestStatus:MutableState<String>,
   requestId:String,context:Context
){

    val response = rememberSaveable() { mutableStateOf("") }
    if(shoutDownDialog.value){
        Dialog(
            onDismissRequest = {shoutDownDialog.value=false},
            properties = DialogProperties(
                usePlatformDefaultWidth = false // This allows the dialog to wrap content
            )
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max)
                    .padding(20.dp)
                    .clip(
                        shape = RoundedCornerShape(10.dp)

                    )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    TextField(
                        value =response.value,
                        onValueChange ={response.value=it},
                        label = { Text(text = hint.value)},
                        colors=TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White,
                            focusedIndicatorColor = colorResource(id = R.color.mainColor),
                            focusedLabelColor = colorResource(id = R.color.mainColor),
                            unfocusedIndicatorColor = colorResource(id = R.color.mainColor),
                            unfocusedLabelColor = Color.Gray,
                            cursorColor = colorResource(id = R.color.mainColor)
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = {
                            shoutDownDialog.value=false
                            updateRequest(requestStatus.value,requestId, response.value,context)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = colorResource(id = R.color.mainColor)
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 40.dp, end = 40.dp)
                    ) {
                        Text(text= stringResource(id = R.string.done))
                    }
                }
            }
        }
    }
}