package com.project.cardmatchgame2.kotlin.model


data class User(val ad: String, val userId: Long) {
    constructor() : this("", 0)
}
