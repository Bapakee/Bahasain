package com.bahasain.data.remote.response.cultural

import com.google.gson.annotations.SerializedName

data class HistoricalResponse(

	@field:SerializedName("data")
	val data: List<DataItemHistorical?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemHistorical(

	@field:SerializedName("imageUrl")
	val imageUrl: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("mapLocation")
	val mapLocation: String? = null
)
