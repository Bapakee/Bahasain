package com.bahasain.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("data")
	val data: DataProfile? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataProfile(

	@field:SerializedName("notif")
	val notif: Int? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("avatar")
	val avatar: String? = null,

	@field:SerializedName("percent")
	val percent: Int? = null,

	@field:SerializedName("certiLink")
	val certiLink: List<Any?>? = null
)
