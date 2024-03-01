package com.example.ourproject.FrontEnd

sealed class ScreensRoute (val route:String){
    object SignIn: ScreensRoute(route="signIn")
    object DonorSignUp: ScreensRoute(route="signUp")
    object FaceScreen: ScreensRoute(route="faceScreen")
    object RegisterAs: ScreensRoute(route="registerAs")
    object OrganizationSignUp: ScreensRoute(route="Or_Sign_Up")
    object FoodContentImages: ScreensRoute(route="foodContent")
    object RequestsScreen: ScreensRoute(route="requests")
    object RequestImages: ScreensRoute(route="requestImages")
    object DonorHome: ScreensRoute(route="donorHome")
    object Response: ScreensRoute(route="response")
}