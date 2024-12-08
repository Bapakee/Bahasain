package com.bahasain.data.remote.response

import com.google.gson.annotations.SerializedName

data class WotdResponse(

	@field:SerializedName("data")
	val data: DataWotd? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataWotd(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("word")
	val word: String? = null
)

data class CategoriesItem(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("translate")
	val translate: String? = null,

	@field:SerializedName("example")
	val example: String? = null
)
