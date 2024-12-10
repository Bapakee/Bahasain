package com.bahasain.data.remote.response.vocab

import com.google.gson.annotations.SerializedName

data class TriviaResponse(

    @field:SerializedName("data")
	val data: DataItemTrivia? = null,

    @field:SerializedName("success")
	val success: Boolean? = null,

    @field:SerializedName("message")
	val message: String? = null
)

data class DataItemTrivia(

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)
