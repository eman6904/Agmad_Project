package com.example.ourproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.ourproject.FrontEnd.screens.foodContent
import com.example.ourproject.FrontEnd.screens.mainScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController= rememberNavController()
           mainScreen(navController)

        }
    }
}