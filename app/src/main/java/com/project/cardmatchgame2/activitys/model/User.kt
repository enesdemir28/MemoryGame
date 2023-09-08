package com.project.cardmatchgame2.activitys.model


data class User(val ad: String, val userId: Long) {
    constructor() : this("", 0)
}
