package com.bahasain.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class SettingResponse(

    @field:SerializedName("data")
	val data: DataSetting? = null,

    @field:SerializedName("success")
	val success: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class DataSetting(
	val any: Any? = null
)
