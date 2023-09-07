package com.project.cardmatchgame2.activitys.model

// User ile ilgili data class
data class User(val ad: String, val userId: Long) {
    constructor() : this("", 0)
}
