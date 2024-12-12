package com.bahasain.data.remote.request

import com.google.gson.annotations.SerializedName

class ProgressRequest(
    @field:SerializedName("moduleId")
    val moduleId: Int,

    @field:SerializedName("levelId")
    val levelId: Int,

    @field:SerializedName("score")
    val score: Int
)