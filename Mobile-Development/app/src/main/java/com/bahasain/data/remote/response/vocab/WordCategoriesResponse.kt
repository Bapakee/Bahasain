package com.bahasain.data.remote.response.vocab

import com.google.gson.annotations.SerializedName

data class WordCategoriesResponse(
	@field:SerializedName("data")
	val data: List<DataItemWord?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemWord(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("word")
	val word: String? = null
)
