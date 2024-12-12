package com.bahasain.data.remote.response.learn

import com.google.gson.annotations.SerializedName

data class LevelResponse(

	@field:SerializedName("data")
	val data: DataLevel? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataLevel(

	@field:SerializedName("isNew")
	val isNew: Boolean? = null
)
