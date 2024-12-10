package com.bahasain.data.remote.response.vocab

import com.google.gson.annotations.SerializedName

data class TranslateResponse(

	@field:SerializedName("data")
	val data: DataTranslate? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataTranslate(
	@field:SerializedName("pos")
	val pos: List<String?>? = null,

	@field:SerializedName("translate")
	val translate: String? = null
)
