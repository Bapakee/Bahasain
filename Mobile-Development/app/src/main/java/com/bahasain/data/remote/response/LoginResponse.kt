package com.bahasain.data.remote.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@field:SerializedName("error")
	val error: String,

	@field:SerializedName("accessToken")
	val accessToken: String,

	@field:SerializedName("refreshToken")
	val refreshToken: String
)
