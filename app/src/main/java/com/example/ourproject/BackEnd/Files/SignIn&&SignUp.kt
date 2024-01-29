package com.example.ourproject.BackEnd.Files

import android.util.Log
import android.widget.Toast
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import com.example.ourproject.BackEnd.DataClasses.UserItems
import com.example.ourproject.FrontEnd.ScreensRoute
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

fun userSignUp(
    name: MutableState<String>,
    email: MutableState<String>,
    password: MutableState<String>,
    phone: MutableState<String>,
    gender: String,
    shoutDownProgress: MutableState<Boolean>,
    showMsgD: MutableState<Boolean>,
    showError: MutableState<Boolean>,
    showMsgV: MutableState<Boolean>,
    navController: NavHostController
) {

    var Auth = FirebaseAuth.getInstance()
    Auth?.createUserWithEmailAndPassword(email.value, password.value)
        ?.addOnCompleteListener(object : OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {

                if (p0.isSuccessful) {

                    var obj = FirebaseDatabase.getInstance().getReference("Donors")
                    var currentUserId = FirebaseAuth.getInstance()?.currentUser!!.uid
                    var donor =
                        UserItems(name.value, email.value, password.value, phone.value, gender)
                    obj?.child(currentUserId)?.setValue(donor)

                    sendEmailVerification(shoutDownProgress, navController, showError)

                } else {
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
        navController.navigate(ScreensRoute.MainScreen.route)

    } else {
        shoutDownProgress.value = false
        showMsgV.value = true
    }
}