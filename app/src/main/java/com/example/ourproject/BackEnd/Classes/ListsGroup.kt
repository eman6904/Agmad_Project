package com.example.ourproject.BackEnd.Classes

import com.example.ourproject.BackEnd.DataClasses.LevelItems
import com.example.ourproject.FrontEnd.screens.levels
import com.example.ourproject.R

class ListsGroup {

    var levels=ArrayList<LevelItems>()

    fun setLevels(){

        levels.add(LevelItems(R.color.red,"Red",0,49))
        levels.add(LevelItems(R.color.orange,"Orange",50,199))
        levels.add(LevelItems(R.color.lightOrange,"Light Orange",200,499))
        levels.add(LevelItems(R.color.yellow,"Yellow",500,999))
        levels.add(LevelItems(R.color.lightBlue,"Light Blue",1000,1499))
        levels.add(LevelItems(R.color.blue,"Blue",1500,2000))
    }
}