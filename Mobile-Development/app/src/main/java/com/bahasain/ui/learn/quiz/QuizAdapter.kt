package com.bahasain.ui.learn.quiz

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemQuizOptionsBinding
import com.dicoding.bahasain.databinding.ItemRearangeQuizBinding

class QuizAdapter(
    private val quizzes: List<QuizModel>,
    private val answeredQuizzes: MutableSet<Int>,
    private val onOptionAnswerSubmitted: (quizId: Int, selectedOption: Int) -> Unit,
    private val onRearrangeAnswerSubmitted: (quizId: Int, userOrder: List<String>) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_quiz_options -> OptionsChoiceViewHolder(
                ItemQuizOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            R.layout.item_rearange_quiz -> RearrangeViewHolder(
                ItemRearangeQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (quizzes[position].type) {
            "option" -> R.layout.item_quiz_options
            "rearrange" -> R.layout.item_rearange_quiz
            else -> throw IllegalArgumentException("Unknown quiz type")
        }
    }

    override fun getItemCount(): Int = quizzes.size

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val quiz = quizzes[position]
        val isAnswered = answeredQuizzes.contains(quiz.id)
        when (holder) {
            is OptionsChoiceViewHolder -> holder.bind(quiz, isAnswered ,onOptionAnswerSubmitted)
            is RearrangeViewHolder -> holder.bind(quiz, isAnswered, onRearrangeAnswerSubmitted)
        }
    }

    class RearrangeViewHolder(private val binding: ItemRearangeQuizBinding) : RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(
            quiz: QuizModel,
            isAnswered: Boolean,
            onRearrangeAnswerSubmitted: (quizId: Int, userOrder: List<String>) -> Unit
        ) {
            val rearrangeView = binding.rearrangeView

            val shuffledOptions = quiz.quizOptions.shuffled()

            rearrangeView.setWords(shuffledOptions)
            rearrangeView.isEnabled = !isAnswered

            binding.btnCheck.setOnClickListener {
                val userOrder = rearrangeView.getUserOrder()
                onRearrangeAnswerSubmitted(quiz.id, userOrder)

                binding.rearrangeView.disableInteraction()
                binding.btnCheck.visibility = View.GONE
                binding.btnCheck.isEnabled = false
            }

            binding.quiz.text = quiz.question
        }
    }


    class OptionsChoiceViewHolder(private val binding: ItemQuizOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(
            quiz: QuizModel,
            isAnswered: Boolean,
            onAnswerSubmitted: (quizId: Int, userAnswer: Int) -> Unit
        ) {
            binding.quiz.text = quiz.question

            if (quiz.imageUrl.isNotEmpty()) {
                binding.ivQuiz.visibility = View.VISIBLE
                Glide.with(binding.root.context)
                    .load(quiz.imageUrl)
                    .into(binding.ivQuiz)
            }

            binding.rgQuiz.removeAllViews()
            binding.rgQuiz.isEnabled = !isAnswered

            quiz.quizOptions.forEachIndexed { index, option ->
                val radioButton = LayoutInflater.from(binding.root.context)
                    .inflate(R.layout.item_options_quiz, binding.rgQuiz, false) as RadioButton

                radioButton.apply {
                    text = option
                    id = index

                    isEnabled = !isAnswered
                }

                radioButton.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        onAnswerSubmitted(quiz.id, index)

                        binding.rgQuiz.isEnabled = false

                        binding.rgQuiz.children.forEach { view ->
                            if (view is RadioButton) {
                                view.isEnabled = false
                            }
                        }
                    }
                }
                binding.rgQuiz.addView(radioButton)
            }
        }
    }
}
