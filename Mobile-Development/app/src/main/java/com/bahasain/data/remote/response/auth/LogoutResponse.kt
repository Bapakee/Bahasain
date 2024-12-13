package com.bahasain.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("data")
	val data: DataLogout? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataLogout(
	val any: Any? = null
)
