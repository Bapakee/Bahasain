package com.bahasain.data.remote.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse (
    @field:SerializedName("error")
    val error: Any
)