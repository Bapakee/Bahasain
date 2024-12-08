package com.bahasain.data.remote.request

import com.google.gson.annotations.SerializedName

data class TranslateRequest (
    @field:SerializedName("text")
    val text: String
)