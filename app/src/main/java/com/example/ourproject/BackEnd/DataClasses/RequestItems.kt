package com.example.ourproject.BackEnd.DataClasses

data class RequestItems(
    var donorName:String="",
    var donorPhone:String="",
    var organizationName:String="",
    var foodState:String="",
    var location:String="",
    var foodContent:String="",
    var mealNumber:String="",
    var  comment:String="",
    var status:String="",
    var date_time:String="",
    var imagesList:List<String> = emptyList()
)
