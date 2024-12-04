package com.bahasain.data.remote.request

import com.google.gson.annotations.SerializedName

class RefreshRequest (
    @field:SerializedName("refreshToken")
    val refreshToken: String
)