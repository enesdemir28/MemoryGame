package com.project.cardmatchgame2.activitys.model

// Skorları tutmak ve kaydetmek için gerekli olan class
data class Score(val ad: String, val score: Int){
    constructor() : this("",0)
}
