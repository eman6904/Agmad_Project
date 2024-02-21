package com.example.ourproject.BackEnd.Files

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.OrganizationItems
import com.example.ourproject.BackEnd.DataClasses.DonorItems
import com.example.ourproject.BackEnd.DataClasses.RequestItems
import com.example.ourproject.FrontEnd.BottomBarScreen
import com.example.ourproject.FrontEnd.ScreensRoute
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*

fun Donor_signUp(
    name: MutableState<String>, email: MutableState<String>,
    password: MutableState<String>, phone: MutableState<String>, gender: String,
    shoutDownProgress: MutableState<Boolean>, showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>, showMsgV: MutableState<Boolean>,
    location: MutableState<String>, navController: NavHostController
) {

    var Auth = FirebaseAuth.getInstance()
    Auth?.createUserWithEmailAndPassword(email.value, password.value)
        ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {

                if (p0.isSuccessful) {

                    var obj = FirebaseDatabase.getInstance().getReference("Donors")
                    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid
                    var donor = DonorItems(
                        name.value,
                        email.value,
                        password.value,
                        phone.value,
                        location.value,
                        gender
                    )
                    obj?.child(currentUserId)?.setValue(donor)

                    sendEmailVerification(shoutDownProgress, navController, showError)

                } else {
                    shoutDownProgress.value = false
                    showMsgD.value = true
                }
            }
        })
}

fun organization_signUp(
    name: MutableState<String>, email: MutableState<String>,
    password: MutableState<String>, taxNumber: MutableState<String>,
    location: MutableState<String>, shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>, showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>, navController: NavHostController
) {

    var Auth = FirebaseAuth.getInstance()
    Auth?.createUserWithEmailAndPassword(email.value, password.value)
        ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {

                if (p0.isSuccessful) {

                    var obj = FirebaseDatabase.getInstance().getReference("Organizations")
                    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid
                    var organization = OrganizationItems(
                        name.value,
                        email.value,
                        password.value,
                        taxNumber.value,
                        location.value
                    )
                    obj?.child(currentUserId)?.setValue(organization)

                    sendEmailVerification(shoutDownProgress, navController, showError)

                } else {
                    shoutDownProgress.value = false
                    showMsgD.value = true
                }
            }
        })
}

fun userSignIn(
    email: MutableState<String>,
    password: MutableState<String>,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    navController: NavHostController
) {

    var Auth = FirebaseAuth.getInstance()
    Auth?.signInWithEmailAndPassword(email.value, password.value)
        ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    //This account exists
                    verifyEmailAddress(shoutDownProgress, navController, showMsgV)
                } else {
                    //this account is not found or there is error
                    shoutDownProgress.value = false
                    showMsgD.value = true
                }
            }
        })
}

private fun sendEmailVerification(
    shoutDownProgress: MutableState<Boolean>,
    navController: NavHostController,
    showError: MutableState<Boolean>
) {
    var Auth = FirebaseAuth.getInstance()
    //send message to verify email
    Auth?.currentUser?.sendEmailVerification()?.addOnCompleteListener {

        if (it.isSuccessful) {

            shoutDownProgress.value = false
            navController.navigate(ScreensRoute.SignIn.route)

        } else {
            //there is problem in sending
            shoutDownProgress.value = false
            showError.value = true
        }
    }
}

private fun verifyEmailAddress(
    shoutDownProgress: MutableState<Boolean>,
    navController: NavHostController,
    showMsgV: MutableState<Boolean>
) {
    //for verify email
    var Auth = FirebaseAuth.getInstance()
    if (Auth?.currentUser!!.isEmailVerified) {
        shoutDownProgress.value = false
        navController.navigate(BottomBarScreen.Home.route)
    } else {
        shoutDownProgress.value = false
        showMsgV.value = true
    }
}

fun uploadImage(imagesLocalUri: MutableList<Uri?>) {

    var storage: StorageReference? = null
    storage = FirebaseStorage.getInstance().reference
    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid


    if (imagesLocalUri != null) {

        for (image in imagesLocalUri) {

            storage?.child(currentUserId+"/"+UUID.randomUUID().toString())?.putFile(image!!)
                ?.addOnSuccessListener {
                    //  Toast.makeText(requireContext(),"Uploaded",Toast.LENGTH_LONG).show()

                }?.addOnFailureListener() {
                    Log.d("message",it.message.toString())
            }

        }

    }
}
fun sendRequest(
     organizationName:String,
     foodState:String ,
     location:String,
     foodContent:MutableState<String>,
     mealNumber:MutableState<String>,
     comment:MutableState<String>,
     imagesList:List<String>
){
    val sdf = SimpleDateFormat("'Date: 'dd-MM-yyyy'   Time: 'HH:mm")
    var calendar=Calendar.getInstance()
    var currentTime= if(calendar.get(Calendar.AM_PM) == Calendar.AM)
        "AM"
    else
        "PM"
    var date_time=sdf.format(Date())+" $currentTime"
    var donorObj = FirebaseDatabase.getInstance().getReference("Donors")
    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid

    donorObj?.child(currentUserId)?.addValueEventListener(object:ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {

            val donor=snapshot.getValue(DonorItems::class.java)
            if(donor!=null){
                var requestObj = FirebaseDatabase.getInstance().getReference("Requests")

                var request=RequestItems(donor!!.name,donor.phone,organizationName,foodState,
                    location,foodContent.value,mealNumber.value,comment.value,"unknown",date_time,imagesList)

                requestObj.push().setValue(request)
            }
        }
        override fun onCancelled(error: DatabaseError) {

        }

    })
}
@Composable
fun getImages():List<String>{

    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid

    var imageUris by remember { mutableStateOf(emptyList<String>()) }

    LaunchedEffect(true) {
        val storageRef = FirebaseStorage.getInstance().reference.child(currentUserId+"/")
        val images = mutableListOf<String>()
        storageRef.listAll().await().items.forEach { imageRef ->
            val uri = imageRef.downloadUrl.await().toString()
            images.add(uri)
        }
        imageUris = images
    }
    return imageUris
}
@Composable
fun getOrganizations():List<String> {

    var imageUris by remember { mutableStateOf(emptyList<String>()) }

    var organizationObj = FirebaseDatabase.getInstance().getReference("Organizations")

    organizationObj?.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

            val images = mutableListOf<String>()
            for (organization in snapshot.children) {

                var orData = organization.getValue(OrganizationItems::class.java)
                images.add(orData!!.name)

            }
            imageUris = images
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        } })
    return imageUris
}
@Composable
fun getRequests():List<RequestItems>{

    var requestList1 by remember { mutableStateOf(emptyList<RequestItems>()) }
    var orName= rememberSaveable() { mutableStateOf("")}
    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid

    var organizationObj = FirebaseDatabase.getInstance().getReference("Organizations")
    organizationObj.child(currentUserId).addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {

               val organizationData=snapshot.getValue(OrganizationItems::class.java)
               if(organizationData!=null)
                   orName.value=organizationData!!.name

        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })

    var requestObj=FirebaseDatabase.getInstance().getReference("Requests")
    requestObj.addValueEventListener(object :ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {

            val requestList2 = mutableListOf<RequestItems>()
            for(request in snapshot.children){

                var requestData=request.getValue(RequestItems::class.java)
                if(requestData?.organizationName==orName.value)
                 requestList2.add(requestData!!)
            }
            requestList1=requestList2
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })
    return requestList1
}