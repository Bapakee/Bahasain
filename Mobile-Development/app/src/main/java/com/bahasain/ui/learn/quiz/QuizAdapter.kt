package com.bahasain.ui.learn.quiz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.bahasain.R
import com.dicoding.bahasain.databinding.ItemQuizOptionsBinding

class QuizAdapter(
    private val quizzes: List<QuizModel>,
    private val onOptionsSelected: (quizId: Int, selectedOption: Int) -> Unit
) : RecyclerView.Adapter<QuizAdapter.OptionsChoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionsChoiceViewHolder {
        val binding = ItemQuizOptionsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OptionsChoiceViewHolder(binding)
    }

    override fun getItemCount(): Int = quizzes.size

    override fun onBindViewHolder(holder: OptionsChoiceViewHolder, position: Int) {
        val quiz = quizzes[position]
        holder.bind(quiz, onOptionsSelected)
    }

    class OptionsChoiceViewHolder(private val binding: ItemQuizOptionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            quiz: QuizModel,
            onOptionsSelected: (quizId: Int, selectedOption: Int) -> Unit
        ) {
            binding.quiz.text = quiz.question

            if (quiz.imageUrl.isNotEmpty()){
                binding.ivQuiz.visibility = View.VISIBLE
                Glide.with(binding.root.context)
                    .load(quiz.imageUrl)
                    .into(binding.ivQuiz)
            }

            binding.rgQuiz.removeAllViews()

            quiz.quizOptions.forEachIndexed { index, option ->
                val radioButton = LayoutInflater.from(binding.root.context)
                    .inflate(R.layout.item_options_quiz, binding.rgQuiz, false) as RadioButton

                radioButton.apply {
                    text = option
                    id = index
                }

                radioButton.setOnCheckedChangeListener{ _, isChecked ->
                    if(isChecked){
                        onOptionsSelected(quiz.id, index)
                    }
                }
                binding.rgQuiz.addView(radioButton)
            }
        }
    }
}
