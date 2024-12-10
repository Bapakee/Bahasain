package com.bahasain.data.remote.response.learn

import com.google.gson.annotations.SerializedName

data class LevelResponse(

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("error")
	val error: String? = null
)
