package com.bahasain.data.remote.request

import com.google.gson.annotations.SerializedName

data class LevelRequest(
    @field:SerializedName("level")
    val level: Int
)