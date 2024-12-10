package com.bahasain.data.remote.response.cultural

import com.google.gson.annotations.SerializedName

data class DetailFolkloreResponse(

	@field:SerializedName("data")
	val data: DataItemDetailFolklore? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemDetailFolklore(

	@field:SerializedName("origin")
	val origin: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
