package com.bahasain.data.pref

data class UserModel (
    val accessToken: String,
    val refreshToken: String,

    val userName: String,
    val userLevel: Int = 0,
)