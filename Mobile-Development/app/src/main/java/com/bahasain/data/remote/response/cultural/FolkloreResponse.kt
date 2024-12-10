package com.bahasain.data.remote.response.cultural

import com.google.gson.annotations.SerializedName

data class FolkloreResponse(

	@field:SerializedName("data")
	val data: List<DataItemFolklore?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemFolklore(

	@field:SerializedName("origin")
	val origin: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null
)
