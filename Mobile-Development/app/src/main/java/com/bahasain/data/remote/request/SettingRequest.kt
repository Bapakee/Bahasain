package com.bahasain.data.remote.request

import com.google.gson.annotations.SerializedName

class SettingRequest (
    @field:SerializedName("avatar")
    val avatar: Int,

    @field:SerializedName("notificationPreference")
    val notificationPreference: Int
)