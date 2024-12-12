package com.bahasain.data.remote.response.learn

import com.google.gson.annotations.SerializedName

data class ProgressResponse(

	@field:SerializedName("data")
	val data: DataItemProgress? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemProgress(

	@field:SerializedName("score")
	val score: Int? = null,

	@field:SerializedName("levelId")
	val levelId: Int? = null,

	@field:SerializedName("levelUp")
	val levelUp: Boolean? = null,

	@field:SerializedName("lastAccessed")
	val lastAccessed: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("completed")
	val completed: Boolean? = null,

	@field:SerializedName("moduleId")
	val moduleId: Int? = null,

	@field:SerializedName("userId")
	val userId: String? = null
)
