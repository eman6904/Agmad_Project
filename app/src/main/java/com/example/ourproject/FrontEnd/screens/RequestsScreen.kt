package com.example.ourproject.FrontEnd.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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

    var requestsList by remember { mutableStateOf(emptyList<RequestItems>()) }

    when(type){

        "Requests"-> requestsList = getRequests()
        "Accepted Requests"-> requestsList = getAcceptedRequested()
        "Rejected Requests"-> requestsList = getRejectedRequested()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        requestsTopBar(navController,type)
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            itemsIndexed(items = requestsList){ index,request->
                requestItem(
                    index,
                    requestsList,
                    navController,
                    type
                )
            }
        }
    }
}
@Composable
fun requestItem(index:Int,requests:List<RequestItems>,navController: NavHostController,requestType:String){

    var currRequest = remember { mutableStateOf(RequestItems())}
    val shoutDownDialog= remember { mutableStateOf(false)}
    if(shoutDownDialog.value)
     requestDisplay(shoutDownDialog = shoutDownDialog, request = currRequest, navController, requestType)
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
            Image(
                painterResource(R.drawable.food_donation_icon2),
                modifier = Modifier
                    .weight(1f)
                    .padding(5.dp),
                contentDescription = "",
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
                    text=requests[index].date_time,
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
    requestsType:String
) {

    var requestStatus= rememberSaveable{ mutableStateOf(request.value.status)}
    var hint= rememberSaveable{ mutableStateOf("")}
    var showDialogForOrganizationResponse= rememberSaveable{ mutableStateOf(false)}
    if(showDialogForOrganizationResponse.value)
        organizationResponse(showDialogForOrganizationResponse,hint,requestStatus,request.value.requestId)
    val scrollState = rememberScrollState()
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
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Name: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    Text(request.value.donorName)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Phone: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    Text(request.value.donorPhone)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Location: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    Text(request.value.location)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Meals Number: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    Text(request.value.mealNumber)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Food Content: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    Text(request.value.foodContent)
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Image List: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    ClickableText(
                        AnnotatedString("Food Content"),
                        onClick = {
                                  navController.navigate(ScreensRoute.RequestImages.route+"/"+request.value.requestId+"/"+requestsType)
                        },
                        style = TextStyle(
                            color=Color.Blue,
                            textDecoration = TextDecoration.Underline
                        )
                    )
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Status: ",
                        fontFamily = FontFamily(Font(R.font.bold))
                    )
                    if(requestStatus.value=="Accepted")
                       Text(requestStatus.value,color=Color.Green)
                    else if(requestStatus.value=="Rejected")
                        Text(requestStatus.value,color=Color.Red)
                    else
                        Text(requestStatus.value)
                }
                if(request.value.organizationResponse.isNotEmpty()){
                    Row(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = "Organization Response: ",
                            fontFamily = FontFamily(Font(R.font.bold))
                        )
                        Text(request.value.organizationResponse)
                    }
                }
                Row(
                    modifier = Modifier.padding(15.dp)
                ) {
                    Text(
                        text = "Comment: ",
                        fontFamily = FontFamily(Font(R.font.bold)),
                    )
                    Text(request.value.comment)
                }
               if(request.value.status=="unknown"){
                   Row(
                       modifier = Modifier
                           .padding(10.dp)
                           .fillMaxWidth(),
                       verticalAlignment = Alignment.CenterVertically,
                       horizontalArrangement = Arrangement.Center
                   ){
                       Button(
                           onClick = {
                               requestStatus.value="Rejected"
                               hint.value="Enter reason for request rejection"
                               showDialogForOrganizationResponse.value=true
                           },
                           colors = ButtonDefaults.buttonColors(
                               contentColor = Color.White,
                               backgroundColor =Color.Red),
                           modifier = Modifier.padding(5.dp)
                       ) {
                           Text(text="Reject")
                       }
                       Button(
                           onClick = {
                               requestStatus.value="Accepted"
                               hint.value="Enter date and time for receive request"
                               showDialogForOrganizationResponse.value=true
                           },
                           colors = ButtonDefaults.buttonColors(
                               contentColor = Color.White,
                               backgroundColor = colorResource(id = R.color.green)),
                           modifier = Modifier.padding(5.dp)
                       ) {
                           Text(text="Accept")
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
   requestId:String
){

    val response = rememberSaveable() { mutableStateOf("") }
    if(shoutDownDialog.value){
        Dialog(
            onDismissRequest = {shoutDownDialog.value=false}
        ) {
            Card(
                modifier = Modifier.clip(shape= RoundedCornerShape(10.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
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
                    Button(
                        onClick = {
                            shoutDownDialog.value=false
                            updateRequest(requestStatus.value,requestId, response.value)
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            backgroundColor = colorResource(id = R.color.mainColor)
                        ),
                        modifier = Modifier.fillMaxWidth().padding(start=10.dp,end=10.dp)
                    ) {
                        Text(text="Done")
                    }
                }
            }
        }
    }
}