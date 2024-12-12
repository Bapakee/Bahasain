package com.bahasain.data.remote.response.learn

import com.google.gson.annotations.SerializedName

data class QuizResponse(

	@field:SerializedName("data")
	val data: List<DataItemQuiz>,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataItemQuiz(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("quizzes")
	val quizzes: List<QuizzesItem>,

	@field:SerializedName("title")
	val title: String? = null
)

data class QuizOptionsItem(

	@field:SerializedName("option")
	val option: String? = null
)

data class QuizzesItem(

	@field:SerializedName("question")
	val question: String? = null,

	@field:SerializedName("answer")
	val answer: String? = null,

	@field:SerializedName("imageUrl")
	val imageUrl: Any? = null,

	@field:SerializedName("quizOptions")
	val quizOptions: List<QuizOptionsItem?>? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("type")
	val type: String? = null
)
