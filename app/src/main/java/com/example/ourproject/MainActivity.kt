package com.example.ourproject

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.ourproject.FrontEnd.screens.foodContent
import com.example.ourproject.FrontEnd.screens.mainScreen
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class MainActivity : ComponentActivity() {

    companion object {

       val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
        // Initialize SharedPreferences
       lateinit var sharedPreferences: SharedPreferences

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            val savedLanguage =sharedPreferences.getString(SELECTED_LANGUAGE, null)
            if (savedLanguage != null) {
                setLocale2(savedLanguage)
            }
            val navController= rememberNavController()
           mainScreen(navController)

        }
    }

}
@Composable
fun setLocale2(lang: String?) {
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val systemLocale = configuration.locale

    // Create a Locale object based on the provided language code
    val newLocale = if (lang != null) Locale(lang) else systemLocale

    // Apply the new locale to the configuration
    val newConfiguration = Configuration(configuration).apply {
        locale = newLocale
    }

    // Update the configuration
    context.resources.updateConfiguration(newConfiguration, context.resources.displayMetrics)

}
