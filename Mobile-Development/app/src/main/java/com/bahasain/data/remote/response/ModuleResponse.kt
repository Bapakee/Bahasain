package com.bahasain.data.remote.response

import com.google.gson.annotations.SerializedName

data class ModuleResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class LevelsItem(

	@field:SerializedName("score")
	val score: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("order")
	val order: Int? = null,

	@field:SerializedName("isCompleted")
	val isCompleted: Boolean? = null
)

data class DataItem(

	@field:SerializedName("isAccessible")
	val isAccessible: Boolean? = null,

	@field:SerializedName("lessonsCompleted")
	val lessonsCompleted: String? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("levels")
	val levels: List<LevelsItem?>? = null
)