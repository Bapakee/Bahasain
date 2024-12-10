package com.bahasain.data.remote.response.auth

import com.google.gson.annotations.SerializedName

data class RefreshResponse(

	@field:SerializedName("data")
	val data: DataItemRefresh? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemRefresh(

	@field:SerializedName("accessToken")
	val accessToken: String? = null
)
