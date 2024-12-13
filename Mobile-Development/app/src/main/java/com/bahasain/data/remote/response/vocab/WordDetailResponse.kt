package com.bahasain.data.remote.response.vocab

import com.google.gson.annotations.SerializedName

data class WordDetailResponse(

	@field:SerializedName("data")
	val data: DataItemDetailWord? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemDetailWord(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesDetailItem?>? = null,

	@field:SerializedName("word")
	val word: String? = null
)

data class CategoriesDetailItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("translate")
	val translate: String? = null,

	@field:SerializedName("example")
	val example: String? = null
)
