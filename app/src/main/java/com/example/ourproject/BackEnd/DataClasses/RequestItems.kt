package com.example.ourproject.BackEnd.DataClasses

data class RequestItems(
    var donorId:String="",
    var requestId:String="",
    var donorName:String="",
    var donorPhone:String="",
    var organizationName:String="",
    var foodState:String="",
    var location:String="",
    var foodContent:String="",
    var mealNumber:String="",
    var comment:String="",
    var status:String="",
    var date_timeOfRequest:String="",
    var date_timeOfResponse:String="",
    var organizationResponse:String="",
    var imagesList:List<String> = emptyList()
)
