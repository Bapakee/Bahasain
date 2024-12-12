package com.bahasain.data.remote.response.learn

import com.google.gson.annotations.SerializedName

data class ScoreResponse(

	@field:SerializedName("data")
	val data: DataItemScore? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemScore(

	@field:SerializedName("streak")
	val streak: Int? = null,

	@field:SerializedName("point")
	val point: Int? = null
)
