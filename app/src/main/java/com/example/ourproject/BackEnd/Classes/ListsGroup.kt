package com.example.ourproject.BackEnd.Classes

import com.example.ourproject.BackEnd.DataClasses.LevelItems
import com.example.ourproject.FrontEnd.screens.levels
import com.example.ourproject.R

class ListsGroup {

    var levels=ArrayList<LevelItems>()
    var channels=ArrayList<Pair<String,Int>>()

    fun setLevels(){

        levels.add(LevelItems(R.color.red,"Red",0,49))
        levels.add(LevelItems(R.color.orange,"Orange",50,199))
        levels.add(LevelItems(R.color.lightOrange,"Light Orange",200,499))
        levels.add(LevelItems(R.color.yellow,"Yellow",500,999))
        levels.add(LevelItems(R.color.lightBlue,"Light Blue",1000,1499))
        levels.add(LevelItems(R.color.blue,"Blue",1500,2000))
    }

    fun setChannels(){

        channels.add(Pair("https://youtube.com/@worldfoodprogramme?si=evvB0HU1g6YbrU4p",R.drawable.wfp))
        channels.add(Pair("https://youtube.com/@unfao?si=cohe6hUT7_Ukilzf",R.drawable.fao))
        channels.add(Pair("https://youtube.com/@foodwastematters?si=PCci_QZm4NEKGF0v",R.drawable.imm2))
        channels.add(Pair("https://www.youtube.com/@toogoodtogoint",R.drawable.togoto ))
        channels.add(Pair("https://www.google.com/url?sa=t&source=web&rct=j&opi=89978449&url=https://ar.wfp.org/stories/11-hqyqt-hwl-hdr-alghdha-wfqdh-wlaqth-balnzm-alghdhayyt-almstdamt&ved=2ahUKEwiL7_LPj8eGAxXvUaQEHdytLToQFnoECC0QAQ&usg=AOvVaw2c6-h6AYRd__7l2Ujy7GHl",R.drawable.stopwaste ))

    }
}